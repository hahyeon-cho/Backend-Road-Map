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
public class DocsLikeAspect {

    @Around("ncnk.make.backendroadmap.domain.aop.Pointcuts.toggleSubCategoryLike()")
    public Object toggleSubCategoryLike(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.info("[소분류 카테고리 좋아요 + 1 트랜잭션 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 소분류 카테고리 좋아요 + 1 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[소분류 카테고리 좋아요 + 1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
