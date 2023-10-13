package travelfeeldog.global.auth.secure;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import travelfeeldog.global.auth.jwt.JwtFilter;
import travelfeeldog.global.auth.jwt.JwtProvider;
import travelfeeldog.global.auth.jwt.JwtService;
import travelfeeldog.infra.oauth2.service.CustomOAuth2UserService;
import travelfeeldog.member.domain.model.Role;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(
                        AbstractHttpConfigurer::disable
                ).cors(
                        AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/","/actuator/health"
                                ,"/api/v1/redis/**"
                                ,"/api/v1/member/**"
                                ,"/swagger-ui/**","/usage" // for swagger
                                ).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .logout(withDefaults())
                .oauth2Login(request -> request.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)));
        httpSecurity.addFilterAfter(new JwtFilter(jwtProvider, jwtService),LogoutFilter.class);


        return httpSecurity.build();
    }
}
