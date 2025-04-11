package ncnk.make.backendroadmap.domain.config;

import ncnk.make.backendroadmap.domain.aop.time.logtrace.LogTrace;
import ncnk.make.backendroadmap.domain.aop.time.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LogTrace 중 동시성 문제 해결을 위해 필요한 Config
 */
@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
