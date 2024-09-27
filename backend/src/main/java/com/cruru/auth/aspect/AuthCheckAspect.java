package com.cruru.auth.aspect;

import com.cruru.auth.annotation.RequireAuthCheck;
import com.cruru.auth.util.AuthChecker;
import com.cruru.auth.util.SecureResource;
import com.cruru.global.LoginProfile;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.IntStream;
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

    private static final String SERVICE_IDENTIFIER = "Service";

    private final ApplicationContext applicationContext;  // 서비스 빈을 동적으로 가져오기 위해 ApplicationContext 사용
    private final MemberService memberService;

    @Before("@annotation(com.cruru.auth.annotation.RequireAuthCheck)")
    public void checkAuthorization(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireAuthCheck authCheck = method.getAnnotation(RequireAuthCheck.class);

        Object[] args = joinPoint.getArgs();                        // 메서드의 실제 인자 값
        String[] parameterNames = signature.getParameterNames();    // 메서드 파라미터 이름들

        LoginProfile loginProfile = extractLoginProfile(parameterNames, args);
        Long targetId = extractTargetId(parameterNames, args, authCheck.targetId());

        Class<? extends SecureResource> domainClass = authCheck.targetDomain();
        authorize(domainClass, targetId, loginProfile);
    }

    private LoginProfile extractLoginProfile(String[] parameterNames, Object[] args) {
        return findParameterByName(parameterNames, args, "loginProfile", LoginProfile.class)
                .orElseThrow(() -> new IllegalArgumentException("loginProfile가 존재하지 않습니다."));
    }

    private Long extractTargetId(String[] parameterNames, Object[] args, String targetIdParamName) {
        return findParameterByName(parameterNames, args, targetIdParamName, Long.class)
                .orElseThrow(() -> new IllegalArgumentException("targetId가 존재하지 않습니다."));
    }

    // 리소스에 대한 권한 검사 로직 분리
    private void authorize(
            Class<? extends SecureResource> domainClass,
            Long targetId,
            LoginProfile loginProfile
    ) throws Throwable {
        try {
            checkAuthorizationForTarget(domainClass, targetId, loginProfile);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(domainClass + ": Service 또는 findById Method가 존재하지 않습니다.");
        }
    }

    // 파라미터 이름과 값을 기반으로 원하는 타입의 파라미터 추출
    private <T> Optional<T> findParameterByName(
            String[] parameterNames,
            Object[] args,
            String targetParamName,
            Class<T> type
    ) {
        return IntStream.range(0, parameterNames.length)
                .filter(i -> parameterNames[i].equals(targetParamName) && type.isInstance(args[i]))
                .mapToObj(i -> type.cast(args[i]))
                .findFirst();
    }

    // targetDomain에 따른 권한 검사 수행
    private void checkAuthorizationForTarget(
            Class<? extends SecureResource> targetDomain,
            Long targetId,
            LoginProfile loginProfile
    ) throws Exception {
        Member member = memberService.findByEmail(loginProfile.email());

        // 도메인 이름을 기반으로 서비스 클래스의 이름을 동적으로 생성
        String targetDomainName = targetDomain.getSimpleName();
        String serviceName =
                Character.toLowerCase(targetDomainName.charAt(0)) + targetDomainName.substring(1) + SERVICE_IDENTIFIER;

        // ApplicationContext를 통해 해당 서비스 빈을 동적으로 가져옴
        Object service = applicationContext.getBean(serviceName);

        // findById 메서드를 호출하여 해당 도메인 객체(SecureResource)를 가져옴
        Method findByIdMethod = service.getClass().getMethod("findById", Long.class);
        SecureResource secureResource = (SecureResource) findByIdMethod.invoke(service, targetId);

        // Lazy Loading된 연관 엔티티를 강제 로딩함
        Hibernate.initialize(secureResource);

        AuthChecker.checkAuthority(secureResource, member);
    }
}
