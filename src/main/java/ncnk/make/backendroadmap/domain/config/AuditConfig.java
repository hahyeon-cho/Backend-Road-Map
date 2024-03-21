package ncnk.make.backendroadmap.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 모든 테이블에 공통으로 필요한 속성값 (BaseTimeEntity를 적용하기 위해 필요한 Config)
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {
}
