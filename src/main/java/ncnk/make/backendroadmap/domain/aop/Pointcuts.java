package ncnk.make.backendroadmap.domain.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {

    // CodingTestService
    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.CodingTestService.evaluateCodingTest(..))")
    public void evaluateCodingTest() {
    }

    // DocsLikeService
    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.DocsLikeService.toggleSubCategoryLike(..))")
    public void toggleSubCategoryLike() {
    }

    // MemberService
    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.MemberService.updateProfile(..))")
    public void memberUpdate() {
    }

    // PracticeCodeService
    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.PracticeCodeService.save(..))")
    public void saveWebCompiler() {
    }

    // SolvedService
    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.SolvedService.recordAttemptedProblem(..))")
    public void recordAttemptedProblem() {
    }

    @Pointcut("execution(public * ncnk.make.backendroadmap.domain.service.SolvedService.solvedProblem(..))")
    public void solvedProblem() {
    }
}
