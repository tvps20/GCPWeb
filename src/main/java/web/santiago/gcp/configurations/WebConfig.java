package web.santiago.gcp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Esta classe define as configurações gerais da aplicação
 * @author Santiago Brothers
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registra uma rota a uma view
     *
     * @param registry ViewControllerRegistry Object
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
