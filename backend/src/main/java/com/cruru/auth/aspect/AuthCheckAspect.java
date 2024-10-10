package com.cruru.auth.aspect;

import com.cruru.auth.annotation.RequireAuth;
import com.cruru.auth.util.AuthChecker;
import com.cruru.auth.util.SecureResource;
import com.cruru.global.LoginProfile;
import com.cruru.member.domain.Member;
import com.cruru.member.service.MemberService;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthCheckAspect {

    private static final String SERVICE_IDENTIFIER = "Service";
    private static final Map<Class<?>, Field[]> fieldCache = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;
    private final MemberService memberService;

    @Before("@annotation(com.cruru.auth.annotation.ValidAuth) && within(com.cruru..controller..*)")
    public void checkAuthorization(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] controllerArgs = joinPoint.getArgs();
        LoginProfile loginProfile = getLoginProfile(controllerArgs);

        for (int i = 0; i < controllerArgs.length; i++) {
            if (!Objects.nonNull(controllerArgs[i])) {
                continue;
            }

            Parameter parameter = parameters[i];
            Object arg = controllerArgs[i];

            if (parameter.isAnnotationPresent(RequireAuth.class) && arg instanceof Long targetId) {
                RequireAuth requireAuth = parameter.getAnnotation(RequireAuth.class);
                checkAuthorizationForTarget(loginProfile, requireAuth.targetDomain(), targetId);
                continue;
            }
            if (!parameter.isAnnotationPresent(RequireAuth.class) && !(arg instanceof Long)) {
                checkDto(loginProfile, arg);
            }
        }
    }

    private LoginProfile getLoginProfile(Object[] args) {
        return Arrays.stream(args, 0, args.length)
                .filter(LoginProfile.class::isInstance)
                .findFirst()
                .map(LoginProfile.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("LoginProfile이 존재하지 않습니다."));
    }

    private void checkDto(LoginProfile loginProfile, Object dto) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(dto);

        while (!stack.isEmpty()) {
            Object current = stack.pop();
            Class<?> clazz = current.getClass();
            Field[] fields = getFields(clazz);

            for (Field field : fields) {
                Object fieldValue;
                try {
                    fieldValue = field.get(current);
                } catch (IllegalAccessException e) {
                    continue;
                }

                if (fieldValue == null) {
                    continue;
                }

                checkEachFields(loginProfile, field, fieldValue);
            }
        }
    }

    private Field[] getFields(Class<?> clazz) {
        return fieldCache.computeIfAbsent(clazz, c -> {
            Field[] declaredFields = c.getDeclaredFields();
            Arrays.stream(declaredFields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                        } catch (InaccessibleObjectException e) {
                            // InaccessibleObjectException 발생 시 무시 (기본 자바 클래스의 필드 접근 시도 시 발생)
                        }
                    });
            return declaredFields;
        });
    }

    private void checkEachFields(LoginProfile loginProfile, Field field, Object fieldValue) {
        if (field.isAnnotationPresent(RequireAuth.class)) {
            RequireAuth requireAuth = field.getAnnotation(RequireAuth.class);
            checkAndAuthorizeField(loginProfile, requireAuth, fieldValue);
        }
    }

    private void checkAndAuthorizeField(LoginProfile loginProfile, RequireAuth requireAuth, Object fieldValue) {
        if (fieldValue instanceof Long targetId) {
            authorize(loginProfile, requireAuth.targetDomain(), targetId);
            return;
        }
        if (fieldValue instanceof Collection<?> collection) {
            collection.stream()
                    .filter(Long.class::isInstance)
                    .map(Long.class::cast)
                    .forEach(id -> authorize(loginProfile, requireAuth.targetDomain(), id));
        }
    }

    private void authorize(LoginProfile loginProfile, Class<? extends SecureResource> domainClass, Long targetId) {
        try {
            checkAuthorizationForTarget(loginProfile, domainClass, targetId);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(domainClass + ": Service 또는 findById Method가 존재하지 않습니다.", e);
        }
    }

    private void checkAuthorizationForTarget(
            LoginProfile loginProfile,
            Class<? extends SecureResource> targetDomain,
            Long targetId
    ) throws ReflectiveOperationException {
        Member member = memberService.findByEmail(loginProfile.email());

        String targetDomainName = targetDomain.getSimpleName();
        String serviceName =
                Character.toLowerCase(targetDomainName.charAt(0)) + targetDomainName.substring(1) + SERVICE_IDENTIFIER;

        Object service = applicationContext.getBean(serviceName);

        Method findByIdMethod = service.getClass().getMethod("findByIdFetchingMember", Long.class);
        Optional<SecureResource> secureResource = (Optional<SecureResource>) findByIdMethod.invoke(service, targetId);

        secureResource.ifPresent(resource -> AuthChecker.checkAuthority(resource, member));
    }
}
