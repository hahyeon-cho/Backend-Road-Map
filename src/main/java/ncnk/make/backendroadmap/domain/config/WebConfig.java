package ncnk.make.backendroadmap.domain.config;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.security.auth.LoginUserArgumentResolver;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * 세션 로그인을 위해 필요한 Config (SpringMVC의 구성 추가)
 */
@RequiredArgsConstructor
@Configuration
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


//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/static/**")
//                    .addResourceLocations("classpath:/static/")
//                    .setCachePeriod(0)
//                    .resourceChain(true)
//                    .addResolver(new PathResourceResolver() {
//                        @Override
//                        protected Resource getResource(String resourcePath, Resource location) throws IOException {
//                            Resource requestedResource = location.createRelative(resourcePath);
//                            return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
//                                    : new ClassPathResource("/static/index.html");
//                        }
//                    });
//
//            // CSS 파일에 대한 MIME 유형 설정 추가
//            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
//            mappings.add("css", "text/css");
//            registry.setOrder(1).addMapping("*.css").addMapping("*.js").addMapping("*.json").addMappings(mappings);
//        }
    }

