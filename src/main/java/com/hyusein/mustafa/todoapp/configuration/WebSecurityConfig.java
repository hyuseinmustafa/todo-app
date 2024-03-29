package com.hyusein.mustafa.todoapp.configuration;

import com.hyusein.mustafa.todoapp.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/registration")
                        .permitAll()
                    .antMatchers("/h2-console/**")
                        .permitAll()
                //added for h2-console access without authentication
                    .anyRequest()
                        .authenticated()
        .and()
                .formLogin()
                    .loginPage("/login").permitAll()
        .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .permitAll()
        .and()
                .csrf()
                    .ignoringAntMatchers("/h2-console/**")
                //added for h2-console access without authentication
        .and()
                .headers()
                    .frameOptions()
                        .sameOrigin()
                //added for h2-console access without authentication
        .and()
                .rememberMe()
                    .key("rememberMeToken")
        .and()
                .sessionManagement()
                .sessionFixation().newSession()
                    .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)//.sessionRegistry(sessionRegistry())
                .and().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        ;
    }
}
