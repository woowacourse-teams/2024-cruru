package com.cruru.auth.aspect;

import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.auth.util.AuthChecker;
import com.cruru.auth.util.SecureResource;
import com.cruru.global.LoginProfile;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAspect {

    private final ApplicationContext applicationContext;  // 서비스 빈을 동적으로 가져오기 위해 ApplicationContext 사용
    private final MemberService memberService;

    @Before("@annotation(com.cruru.auth.annotation.RequireAuthCheck)")
    public void checkAuthorization(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireAuthCheck authCheck = method.getAnnotation(RequireAuthCheck.class);

        Object[] args = joinPoint.getArgs();  // 메서드의 실제 인자 값
        String[] parameterNames = signature.getParameterNames();  // 메서드 파라미터 이름들

        Long targetId = null;
        LoginProfile loginProfile = null;

        // 어노테이션에서 지정한 파라미터 이름을 기반으로 해당 파라미터 값을 찾음
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(authCheck.targetId())) {
                targetId = (Long) args[i];  // targetIdParam에 맞는 파라미터 값 추출
            } else if (parameterNames[i].equals("loginProfile")) {
                loginProfile = (LoginProfile) args[i];
            }
        }

        if (targetId == null || loginProfile == null) {
            throw new IllegalArgumentException("targetId, loginProfile가 존재하지 않습니다.");
        }

        String targetDomain = authCheck.targetDomain().getName();
        // 도메인 이름을 기반으로 서비스 클래스 주입 및 권한 검사
        try {
            checkAuthorizationForTarget(targetDomain, targetId, loginProfile);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(targetDomain + ": Service 또는 findById Method가 존재하지 않습니다.");
        }
    }

    // targetDomain에 따른 권한 검사 수행
    private void checkAuthorizationForTarget(String targetDomain, Long targetId, LoginProfile loginProfile) throws Exception {
        Member member = memberService.findByEmail(loginProfile.email());

        // 도메인 이름을 기반으로 서비스 클래스의 이름을 동적으로 생성
        String serviceName = targetDomain + "Service";

        // ApplicationContext를 통해 해당 서비스 빈을 동적으로 가져옴
        Object service = applicationContext.getBean(serviceName);

        // findById 메서드를 호출하여 해당 도메인 객체(SecureResource)를 가져옴
        Method findByIdMethod = service.getClass().getMethod("findById", Long.class);
        SecureResource secureResource = (SecureResource) findByIdMethod.invoke(service, targetId);

        // Lazy Loading된 연관 엔티티를 강제 로딩함
        Hibernate.initialize(secureResource);

        // AuthChecker를 통해 권한 검사 수행
        AuthChecker.checkAuthority(secureResource, member);
    }
}
