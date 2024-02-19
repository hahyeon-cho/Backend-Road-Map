package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    //    @GetMapping("/home")
//    public String home(@RequestParam String path) {
//        if (path.equals("RoadMap")) {
//            return "category/roadMap";
//        }
//        if (path.equals("Practice")) {
//            return "category/practice";
//        }
//        if (path.equals("Coding Test")) {
//            return "category/codingTest";
//        }
//        throw new UndefinedAddressException();
//    }
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "home";
    }
}
