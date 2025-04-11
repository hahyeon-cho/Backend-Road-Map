package ncnk.make.backendroadmap.domain.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.security.auth.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 세션 로그인을 위해 필요한 Config (SpringMVC의 구성 추가)
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    /**
     * LoginUserArgumentResolver는 사용자 로그인 정보를 처리하는 HandlerMethodArgumentResolver의 구현체 addArgumentResolvers 메서드를 통해
     * 추가함으로써, 컨트롤러 메서드에서 로그인 사용자 정보를 파라미터로 직접 받을 수 있음.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}

