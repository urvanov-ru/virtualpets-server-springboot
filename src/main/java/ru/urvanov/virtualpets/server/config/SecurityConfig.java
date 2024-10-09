package ru.urvanov.virtualpets.server.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import ru.urvanov.virtualpets.server.auth.CustomAuthenticationEntryPoint;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChainSite(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {
        return http
                .securityMatcher( "/site/**")
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/site/admin/**")
                        .hasRole("ADMIN")
                    .requestMatchers("/site/user/**")
                        .hasRole("USER")
                    .requestMatchers("/site/**")
                        .permitAll()
                )
                
                .csrf(Customizer.withDefaults())
                .formLogin((formLogin) ->
                    formLogin.loginPage("/site/login")
                        .loginProcessingUrl("/site/login")
                        .defaultSuccessUrl("/site/user/profile")
                        .failureUrl("/site/login?error=1")
                )
                .logout((logout) ->
                    logout.logoutUrl("/site/logout")
                        .logoutSuccessUrl("/site/login?logout=1")
                )
                .build();
    }

    
    @Bean
    public SecurityFilterChain securityFilterChainApi(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint
            ) throws Exception {
        return http
            .securityMatcher("/api/**")
            .authenticationManager(authenticationManager)
            .exceptionHandling(e -> 
                    e.authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests((authorize) -> 
                authorize.requestMatchers("/api/v1/PublicService/**")
                        .permitAll()
                        .requestMatchers("/api/**").hasRole("USER")
            )
            // По умолчанию используется настройка из Spring MVC.
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }

    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider authenticationProvider) {
        return new ProviderManager(List.of(authenticationProvider));
    }
}
