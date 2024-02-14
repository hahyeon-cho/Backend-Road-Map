package com.jojoldu.springboot.config.auth;

import com.make.backendroadmap.domain.security.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile");
//                .requestMatchers(new AntPathRequestMatcher("/"))
//                .requestMatchers(new AntPathRequestMatcher("/css/**"))
//                .requestMatchers(new AntPathRequestMatcher("/images/**"))
//                .requestMatchers(new AntPathRequestMatcher("/js/**"))
//                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                .requestMatchers(new AntPathRequestMatcher("/profile"));
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header
                        .frameOptions(frameOptions -> frameOptions.sameOrigin().disable()))

                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
//                                .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated())
//                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
//                .formLogin((formLogin) ->
//                        formLogin
//                                .loginPage("/login/login")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .loginProcessingUrl("/login/login-proc")
//                                .defaultSuccessUrl("/", true)
//                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/"))
                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/login")
//                        .successHandler(successHandler())
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)));

        return http.build();
    }
}

