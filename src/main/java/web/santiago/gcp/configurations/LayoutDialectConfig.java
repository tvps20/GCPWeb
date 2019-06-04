package web.santiago.gcp.configurations;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Esta classe é responsavel por configurar os dialetos utilizado pelo template render do thymeleaf
 *
 * @author Santiago Brothers
 */
@Configuration
public class LayoutDialectConfig {

    /**
     * Cria um novo objeto de dialeto para ser utilizado pelo render do thymeleaf
     *
     * @return LayoutDialect Object
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
