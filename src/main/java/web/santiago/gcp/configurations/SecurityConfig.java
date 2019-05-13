package web.santiago.gcp.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Essa classe define as configurações de seguranças, autorizações e autenticações da aplicação.
 *
 * @author Santiago Brothers
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * Faz uma busca no banco e resupera o nome de usuario a senha permitindo se logar no sistema
     *
     * @param authenticationManagerBuilder classe de autenticação
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .authoritiesByUsernameQuery("select USERNAME, ROLE from USER where USERNAME=?")
                .usersByUsernameQuery("select USERNAME, PASSWORD, 1 as enable from USER where USERNAME=?")
                .passwordEncoder(new BCryptPasswordEncoder());
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
     * Configura todas as permissões de acesso da aplicação
     *
     * @param http Spring Security Http Object
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/create", "/user/save").permitAll()
                .antMatchers("/resources/**", "/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() /*INFORMANDO O CAMINHO DA PÁGINA DE LOGIN, E SE O LOGIN FOR EFETUADO COM SUCESSO O USUÁRIO DEVE SER REDIRECIONADO PARA /home(http://localhost:8095/)*/
                .loginPage("/login").defaultSuccessUrl("/", true)
                .permitAll() /*AQUI ESTAMOS INFORMANDO QUE TODOS TEM ACESSO A PÁGINA DE LOGIN*/
                .and()
                .logout()
                .permitAll();
    }
}