package ncnk.make.backendroadmap.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * [멤버 포인트 계산] 트랜잭션 AOP
 */
@Slf4j
@Aspect
public class SolvedAspect {

    @Around("execution(public void ncnk.make.backendroadmap.domain.aop.Pointcuts.recordAttemptedProblem())")
    public Object recordAttemptedProblem(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[문제를 풀려고 한 경우 Solved 테이블 컬럼 추가] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[문제를 풀려고 한 경우 Solved 테이블 컬럼 추가 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[문제를 풀려고 한 경우 Solved 테이블 컬럼 추가 트랜잭션 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 문제를 풀려고 한 경우 Solved 테이블 컬럼 추가  : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[문제를 풀려고 한 경우 Solved 테이블 컬럼 추가 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("execution(public void ncnk.make.backendroadmap.domain.aop.Pointcuts.solvedProblem())")
    public Object solvedProblem(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[문제를 푼 경우 로직 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[문제를 푼 경우 로직 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[문제를 푼 경우 로직 트랜잭션 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 문제를 푼 경우 로직 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[문제를 푼 경우 로직 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
