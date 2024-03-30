package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 소분류 페이지 Controller
 */
@Controller
@RequestMapping("/roadmap/sub")
@RequiredArgsConstructor // 롬복을 사용하여 생성자 주입을 자동으로 생성
public class PageController {
    private final MainCategoryService mainCategoryService;

    //id값으로 url 변경
    @GetMapping("/{id}")
    public String internet(@PathVariable Long id, Model model) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(id);
        if (mainCategory != null) {
            String mainDocsTitle = String.valueOf(mainCategory.getMainDocsTitle());
            model.addAttribute("mainDocsTitle", mainDocsTitle);
            return "/DataSet/" + mainDocsTitle + "/" + mainDocsTitle; // 반환
        } else {
            // MainCategory가 없는 경우 에러 페이지 반환
            return "error";
        }
    }
}

//public class PageController {
//    @GetMapping("/1")
//    public String internet() {
//        return "/DataSet/01_Internet/Internet";
//    }
//
//    @GetMapping("/2")
//    public String basicFE() {
//        return "/DataSet/02_Basic-FE/Basic-FE";
//    }
//
//
//    @GetMapping("/3")
//    public String os() {
//        return "/DataSet/03_OS-work/OS";
//    }
//
//
//    @GetMapping("/4")
//    public String language() {
//        return "/DataSet/04_Language/Language";
//    }
//
//
//    @GetMapping("/5")
//    public String algorithm() {
//        return "/DataSet/05_Algorithm/Algorithm";
//    }
//
//
//    @GetMapping("/6")
//    public String git() {
//        return "/DataSet/06_Git/Git";
//    }
//
//
//    @GetMapping("/7")
//    public String gitHubGitLab() {
//        return "/DataSet/07_GitHub-GitLab/GitHub-GitLab";
//    }
//
//
//    @GetMapping("/8")
//    public String RDB() {
//        return "/DataSet/08_RDB/RDB";
//    }
//
//
//    @GetMapping("/9")
//    public String noSQL() {
//        return "/DataSet/09_NoSQL/NoSQL-DB";
//    }
//
//
//    @GetMapping("/10")
//    public String DbKnowledge() {
//        return "/DataSet/10_DB-Knowledge/DB-Knowledge";
//    }
//
//
//    @GetMapping("/11")
//    public String APIs() {
//        return "/DataSet/11_APIs/APIs";
//    }
//
//
//    @GetMapping("/12")
//    public String framework() {
//        return "/DataSet/12_Framework/Framework";
//    }
//
//
//    @GetMapping("/13")
//    public String caching() {
//        return "/DataSet/13_Caching/caching";
//    }
//
//
//    @GetMapping("/14")
//    public String WebSecurity() {
//        return "/DataSet/14_Web-Security/Web-Security";
//    }
//
//
//    @GetMapping("/15")
//    public String test() {
//        return "/DataSet/15_Test/Test";
//    }
//
//
//    @GetMapping("/16")
//    public String CICD() {
//        return "/DataSet/16_CI-CD/CI-CD";
//    }
//
//
//    @GetMapping("/17")
//    public String DesignPattern() {
//        return "/DataSet/17_Design-Pattern/Design-Pattern";
//    }
//
//
//    @GetMapping("/18")
//    public String SearchEngines() {
//        return "/DataSet/18_SearchEngines/SearchEngines";
//    }
//
//
//    @GetMapping("/19")
//    public String container() {
//        return "/DataSet/19_Container/Container";
//    }
//
//
//    @GetMapping("/20")
//    public String WebServer() {
//        return "/DataSet/20_Web-Server/Web-Server";
//    }
//}
