package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController {
    // 이 매핑은 프로필을 수정하기 위한 페이지를 불러옵니다. 사용자가 프로필을 수정하고자 할 때 먼저 해당 페이지로 이동해야 합니다.
    // 이 매핑은 GET 요청을 처리하여 프로필 수정 페이지를 보여줍니다.
    @GetMapping()
    public String updateProfile() {
        return "Login/login";
    }
}




