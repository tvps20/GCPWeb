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
import web.santiago.gcp.services.CustumUserDetailService;

import static web.santiago.gcp.configurations.SecurityConstants.SEARCH_URL;
import static web.santiago.gcp.configurations.SecurityConstants.SIGN_UP_URL;

/**
 * Essa classe define as configurações de seguranças, autorizações e autenticações da aplicação.
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
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
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
}