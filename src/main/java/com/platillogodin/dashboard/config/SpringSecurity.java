package com.platillogodin.dashboard.config;

import com.platillogodin.dashboard.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;

    public SpringSecurity(AccessDeniedHandler accessDeniedHandler, UserDetailsServiceImpl userDetailsService) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint apiAuthenticationEntryPoint() {
        return new ApiAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/403", "/error","/api/**")
                .permitAll()
                .antMatchers("/recipes",
                        "/recipes/**",
                        "/ingredients/**",
                        "/categories/**", "/",
                        "/index",
//                        "/api/**",
                        "/users/profile/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/**")
                .hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().rememberMe().userDetailsService(userDetailsService)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .defaultAuthenticationEntryPointFor(apiAuthenticationEntryPoint(), new AntPathRequestMatcher("/api/**")
        )
        ;
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderBean());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/images/**")
                .antMatchers("/h2-console/**/**");
    }

}