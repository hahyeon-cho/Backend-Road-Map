package com.make.backendroadmap.web;


import com.make.backendroadmap.domain.security.auth.LoginUser;
import com.make.backendroadmap.domain.security.auth.dto.SessionUser;
import com.make.backendroadmap.domain.security.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/index")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        log.info("call IndexController!");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
