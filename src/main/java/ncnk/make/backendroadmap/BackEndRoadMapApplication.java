package ncnk.make.backendroadmap;

import ncnk.make.backendroadmap.domain.aop.CodingTestAspect;
import ncnk.make.backendroadmap.domain.aop.DocsLikeAspect;
import ncnk.make.backendroadmap.domain.aop.MemberAspect;
import ncnk.make.backendroadmap.domain.aop.PracticeCodeAspect;
import ncnk.make.backendroadmap.domain.aop.SolvedAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@Import({
    CodingTestAspect.class,
    DocsLikeAspect.class,
    MemberAspect.class,
    PracticeCodeAspect.class,
    SolvedAspect.class
})
public class BackEndRoadMapApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndRoadMapApplication.class, args);
    }
}
