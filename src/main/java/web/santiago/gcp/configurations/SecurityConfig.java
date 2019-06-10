package web.santiago.gcp.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import web.santiago.gcp.services.CustumUserDetailService;

import java.util.Arrays;

import static web.santiago.gcp.configurations.SecurityConstants.SIGN_UP_URL;

/**
 * Essa classe define as configurações de seguranças, autorizações e
 * autenticações da aplicação.
 *
 * @author Santiago Brothers
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustumUserDetailService custumUserDetailService;

    /**
     * Configura todas as permissões de acesso da aplicação
     *
     * @param http Spring Security Http Object
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(SIGN_UP_URL).permitAll().antMatchers("/**").hasRole("USER").and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), custumUserDetailService));
    }

    /**
     * Método responsavel por descriptografar a senha recuperada do banco
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(custumUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Método responsavel por descriptografar a senha recuperada do banco
     *
     * @return nova instancia da classe BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Metodo para liberar o acesso externo a requisições http
     *
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("x-xsrf-token");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Access-Control-Allow-Headers");
        configuration.addAllowedHeader("access-control-allow-methods");
        configuration.addAllowedHeader("access-control-allow-origin");
        configuration.addAllowedHeader("Origin");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("X-Requested-With");
        configuration.addAllowedHeader("Access-Control-Request-Method");
        configuration.addAllowedHeader("Access-Control-Request-Headers");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}