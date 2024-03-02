package ncnk.make.backendroadmap.domain.config;

import ncnk.make.backendroadmap.domain.aop.time.logtrace.LogTrace;
import ncnk.make.backendroadmap.domain.aop.time.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
