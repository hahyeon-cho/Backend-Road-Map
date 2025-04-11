package ncnk.make.backendroadmap.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * [회원 프로필 업데이트] 트랜잭션 AOP
 */
@Aspect
@Slf4j
public class MemberAspect {

    @Around("ncnk.make.backendroadmap.domain.aop.Pointcuts.memberUpdate()")
    public Object memberUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.info("[회원정보 업데이트 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 회원정보 업데이트 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[회원정보 업데이트 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
