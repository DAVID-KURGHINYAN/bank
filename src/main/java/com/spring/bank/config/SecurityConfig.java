package com.spring.bank.config;

import com.spring.bank.enums.Role;
import com.spring.bank.security.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(
                        Role.USER.name(),
                        Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasAnyRole(
                        Role.USER.name(),
                        Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasAnyRole(
                        Role.USER.name(),
                        Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(
                        Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//        @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("user")
//                        .password(encoder().encode("user"))
//                        .roles(Role.USER.name())
//                        .build(),
//                User.builder()
//                        .username("admin")
//                        .password(encoder().encode("admin"))
//                        .roles(Role.ADMIN.name())
//                        .build()
//        );
//    }

    @Bean
    protected PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(12);
    }
}
