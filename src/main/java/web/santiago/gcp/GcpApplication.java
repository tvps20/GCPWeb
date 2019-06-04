package web.santiago.gcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Representa uma aplicação Spring Boot
 *
 * @author Santiago Brothers
 */
@SpringBootApplication
@EnableJpaAuditing
public class GcpApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GcpApplication.class);
    }

    /**
     * Ponto de partida da aplicação
     *
     * @param args Argumentos passado pelo console durante a inicialização
     */
    public static void main(String[] args) {
        SpringApplication.run(GcpApplication.class, args);
    }

}
