package com.itmo.springproject01.configuration;

import com.itmo.springproject01.service.JwtAuthenticationFilter;
import com.itmo.springproject01.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // boolean securedEnabled() default false;
public class SpringSecurityConfiguration {
    private UserDetailService userDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SpringSecurityConfiguration(UserDetailService userDetailService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailService = userDetailService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // /api/admin - jwt
    // /account - form
    /*@Bean
    @Order(1) // SecurityFilterChain
    public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/admin/**")
                .authenticationProvider()
                .authorizeHttpRequests((authorize) -> authorize
                        //.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers().permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }*/

    // org.springframework.security.config.annotation.web.configurers;
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/account/**", "/front/**", "/picture/**")
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/account/registration", "/account/login", "/front/**", "/picture/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/account")
//                         .requestMatchers(HttpMethod.GET)
                        .hasRole("USER") // .hasAnyRole("ADMIN" , "MODERATOR")
                        // .hasAuthority("") .hasAnyAuthority()
                        .anyRequest()
                        .authenticated()) // /account/login         /login
                .formLogin(form -> form
                        .usernameParameter("user_name") // значение атрибута name в html форме
                        .passwordParameter("user_password") // значение атрибута name в html форме
                        .loginPage("/account/login") // форма доступна по адресу
                        .loginProcessingUrl("/account/login") // обработчик, значение атрибута action тега form
                        .failureUrl("/account/login?failed") // ошибка авторизации
                        .defaultSuccessUrl("/account") // перенаправление после успешной авторизации
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/account/logout") // <a th:href="@{/account/logout}">Выйти</a>
                        .logoutSuccessUrl("/account/login") // перенаправление после /account/logout
                        .permitAll())  // [ВЫЙТИ] /account/logout
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


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                                   config)  {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
