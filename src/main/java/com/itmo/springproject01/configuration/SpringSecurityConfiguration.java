package com.itmo.springproject01.configuration;

import com.itmo.springproject01.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // boolean securedEnabled() default false;
public class SpringSecurityConfiguration {
    private UserDetailService userDetailService;

    @Autowired
    public SpringSecurityConfiguration(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // org.springframework.security.config.annotation.web.configurers;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/account/registration", "/account/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/picture")
                        // .requestMatchers(HttpMethod.GET)
                        .hasRole("USER") // .hasAnyRole("ADMIN" , "MODERATOR")
                        // .hasAuthority("") .hasAnyAuthority()
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form
                        .usernameParameter("username") // значение атрибута name в html форме
                        .passwordParameter("password") // значение атрибута name в html форме
                        .loginPage("/account/login") // форма доступна по адресу
                        .loginProcessingUrl("/account/login") // обработчик, значение атрибута action тега form
                        .failureUrl("/account/login?failed") // ошибка авторизации
                        .defaultSuccessUrl("/account") // перенаправление после успешной авторизации
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/account/logout")
                        .logoutSuccessUrl("/account/login") // перенаправление после /account/logout
                        .permitAll())
                .build();
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
