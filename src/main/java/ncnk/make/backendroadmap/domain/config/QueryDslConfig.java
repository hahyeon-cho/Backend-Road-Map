package ncnk.make.backendroadmap.domain.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDsl 라이브러리를 위해 필요한 Config
 */
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory init() {
        return new JPAQueryFactory(em);
    }
}
