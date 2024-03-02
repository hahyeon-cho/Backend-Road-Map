package ncnk.make.backendroadmap.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class PracticeCodeAspect {
    @Around("ncnk.make.backendroadmap.domain.aop.Pointcuts.saveWebCompiler()")
    public Object downLoadWebCompiler(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[웹 컴파일러 다운로드 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[웹 컴파일러 다운로드 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[웹 컴파일러 다운로드 트랜잭션 롤백] {}", joinPoint.getSignature());
            log.error("[ERROR] 웹 컴파일러 다운로드 : {}", e.getMessage());
            throw e;
        } finally {
            log.info("[웹 컴파일러 다운로드 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
