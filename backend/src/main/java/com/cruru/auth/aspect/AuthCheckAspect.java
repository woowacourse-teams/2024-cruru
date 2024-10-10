package com.cruru.auth.aspect;

import com.cruru.advice.NotFoundException;
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
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthCheckAspect {

    private static final String REPOSITORY_SUFFIX = "Repository";
    private static final String REPOSITORY_METHOD_NAME = "findByIdFetchingMember";
    private static final Map<Class<?>, Field[]> FIELD_CACHE = new ConcurrentHashMap<>();

    private final ApplicationContext applicationContext;
    private final MemberService memberService;

    @Before("@annotation(com.cruru.auth.annotation.ValidAuth) && within(com.cruru..controller..*)")
    public void checkAuthorization(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] controllerArgs = joinPoint.getArgs();
        LoginProfile loginProfile = extractLoginProfile(controllerArgs);

        for (int i = 0; i < controllerArgs.length; i++) {
            if (controllerArgs[i] == null) {
                continue;
            }

            Parameter parameter = method.getParameters()[i];
            Object arg = controllerArgs[i];

            if (parameter.isAnnotationPresent(RequireAuth.class) && arg instanceof Long targetId) {
                authorize(loginProfile, parameter.getAnnotation(RequireAuth.class).targetDomain(), targetId);
            } else if (!(arg instanceof Long)) {
                processDtoFields(loginProfile, arg);
            }
        }
    }

    private LoginProfile extractLoginProfile(Object[] args) {
        return Arrays.stream(args)
                .filter(LoginProfile.class::isInstance)
                .map(LoginProfile.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("LoginProfile이 존재하지 않습니다."));
    }

    private void processDtoFields(LoginProfile loginProfile, Object dto) {
        Deque<Object> dtoStack = new ArrayDeque<>();
        dtoStack.push(dto);

        while (!dtoStack.isEmpty()) {
            Object currentDto = dtoStack.pop();
            for (Field field : retrieveFields(currentDto.getClass())) {
                Object fieldValue = safelyGetFieldValue(field, currentDto);
                if (fieldValue == null) {
                    continue;
                }
                processField(loginProfile, field, fieldValue);
            }
        }
    }

    private Field[] retrieveFields(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> {
            Field[] declaredFields = c.getDeclaredFields();
            Arrays.stream(declaredFields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                        } catch (InaccessibleObjectException ignored) {
                            // Java 기본 클래스 접근시 발생 예외 무시
                        }
                    });
            return declaredFields;
        });
    }

    private Object safelyGetFieldValue(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException ignored) {
            return null;
        }
    }

    private void processField(LoginProfile loginProfile, Field field, Object fieldValue) {
        if (field.isAnnotationPresent(RequireAuth.class)) {
            RequireAuth requireAuth = field.getAnnotation(RequireAuth.class);
            authorizeField(loginProfile, requireAuth, fieldValue);
        }
    }

    private void authorizeField(LoginProfile loginProfile, RequireAuth requireAuth, Object fieldValue) {
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
            Member member = memberService.findByEmail(loginProfile.email());
            String domainName = domainClass.getSimpleName();
            String repositoryName = getServiceName(domainName);
            Object repository = applicationContext.getBean(repositoryName);
            Method findByIdFetchingMember = repository.getClass().getMethod(REPOSITORY_METHOD_NAME, Long.class);
            Optional<SecureResource> resourceOpt = (Optional<SecureResource>) findByIdFetchingMember.invoke(
                    repository,
                    targetId
            );

            resourceOpt.ifPresentOrElse(
                    resource -> AuthChecker.checkAuthority(resource, member),
                    () -> {
                        throw new NotFoundException(domainClass.getSimpleName());
                    }
            );
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    domainClass.getSimpleName() + ": Repository 또는 findByIdFetchingMember Method가 존재하지 않습니다.");
        }
    }

    private String getServiceName(String domainName) {
        return Character.toLowerCase(domainName.charAt(0)) + domainName.substring(1) + REPOSITORY_SUFFIX;
    }
}
