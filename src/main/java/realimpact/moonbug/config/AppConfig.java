package realimpact.moonbug.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import realimpact.moonbug.domain.menu.MenuCategory;
import realimpact.moonbug.domain.menu.MenuSize;
import realimpact.moonbug.util.enumutil.EnumMapper;

import javax.persistence.EntityManager;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AppConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {

            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.of("강인수");
            }

        };
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        //
        return new JPAQueryFactory(em);
    }

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("menuCategory", MenuCategory.class);
        enumMapper.put("menuSize", MenuSize.class);
        return enumMapper;
    }

}