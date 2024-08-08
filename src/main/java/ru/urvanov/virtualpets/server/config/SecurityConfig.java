package ru.urvanov.virtualpets.server.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import ru.urvanov.virtualpets.server.auth.CustomAuthenticationEntryPoint;
import ru.urvanov.virtualpets.server.auth.DatabaseAuthenticationProvider;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    
    public static String REMEMBER_ME_GAME_KEY = "virtualpets-server-springboot-game";
    
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
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
            .securityMatcher("/rest/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> 
                authorize.requestMatchers("/rest/v1/PublicService/**")
                    .permitAll()
                .requestMatchers("/rest/**").hasRole("USER")
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
    public SecurityContextRepository securityContextRepository(
            List<SecurityContextRepository> securityContextRepositoryList
            ) {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }

    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public DatabaseAuthenticationProvider authenticationProvider(
            UserDetailsService userService,
            BCryptPasswordEncoder bcryptEncoder) {
        DatabaseAuthenticationProvider databaseAuthenticationProvider
                = new DatabaseAuthenticationProvider();
        databaseAuthenticationProvider.setUserDetailsService(userService);
        databaseAuthenticationProvider.setBcryptEncoder(bcryptEncoder);
        return databaseAuthenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            DatabaseAuthenticationProvider gameAuthenticationProvider) {
        return new ProviderManager(List.of(gameAuthenticationProvider));
    }
}
