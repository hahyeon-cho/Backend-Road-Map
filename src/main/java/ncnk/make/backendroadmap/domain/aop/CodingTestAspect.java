package ncnk.make.backendroadmap.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * [소분류 카테고리 좋아요 + 1] 트랜잭션 AOP
 */
@Aspect
@Slf4j
public class CodingTestAspect {

    @Around("ncnk.make.backendroadmap.domain.aop.Pointcuts.evaluateCodingTest()")
    public Object evaluateCodingTest(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.info("[사용자 답과 정답 비교 트랜잭션 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 사용자 답과 정답 비교 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[사용자 답과 정답 비교 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
