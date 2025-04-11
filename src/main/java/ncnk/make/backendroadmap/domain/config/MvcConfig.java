package ncnk.make.backendroadmap.domain.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 모든 테이블에 공통으로 필요한 속성값 (BaseTimeEntity를 적용하기 위해 필요한 Config)
 */
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/templates/", "classpath:/static/");
                //.setCacheControl()
    }
}
