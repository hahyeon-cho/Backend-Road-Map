package ncnk.make.backendroadmap.domain.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //DocsLikeService
    @Pointcut("execution(public void ncnk.make.backendroadmap.domain.service.DocsLikeService.toggleSubCategoryLike(ncnk.make.backendroadmap.domain.entity.Member,ncnk.make.backendroadmap.domain.entity.SubCategory))")
    public void toggleSubCategoryLike() {
    }

    //MemberService
    @Pointcut("execution(public void ncnk.make.backendroadmap.domain.service.MemberService.updateProfile(ncnk.make.backendroadmap.domain.entity.Member,ncnk.make.backendroadmap.domain.restController.dto.Member.MemberUpdateRequestDto))")
    public void memberUpdate() {
    }

    //PracticeCodeService
    @Pointcut("execution(public void ncnk.make.backendroadmap.domain.service.PracticeCodeService.save(String, String, String, ncnk.make.backendroadmap.domain.entity.Member))")
    public void saveWebCompiler() {
    }

    //SolvedService
    @Pointcut("execution(public void ncnk.make.backendroadmap.domain.service.SolvedService.calculatePoint(ncnk.make.backendroadmap.domain.entity.Member, ncnk.make.backendroadmap.domain.entity.CodingTest))")
    public void calculateMemberPoint() {
    }

}
