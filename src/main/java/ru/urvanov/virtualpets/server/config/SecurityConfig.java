package ru.urvanov.virtualpets.server.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    
    public static String REMEMBER_ME_GAME_KEY = "virtualpets-server-springboot-game";

    @Bean
    public SecurityFilterChain securityFilterChainSite(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {
        return http
                .securityMatcher( "/site/**")
                .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/site/admin/**")
                        .hasRole("ADMIN")
                    .requestMatchers("/site/user/**")
                        .hasRole("USER")
                    .requestMatchers("/site/**")
                        .permitAll()
                )
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
                .authenticationManager(authenticationManager)
                .build();
    }

    
    @Bean
    public SecurityFilterChain securityFilterChainGame(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            AuthenticationEntryPoint authenticationEntryPoint
            ) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> 
                authorize.requestMatchers("/api/v1/PublicService/**")
                    .permitAll()
                .requestMatchers("/api/**").hasRole("USER")
            )
            .securityContext((securityContext) -> securityContext
                    .securityContextRepository(securityContextRepository)
                )
            .exceptionHandling(eH -> 
                   eH.authenticationEntryPoint(authenticationEntryPoint))
            .authenticationManager(authenticationManager);
        return http.build();
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
