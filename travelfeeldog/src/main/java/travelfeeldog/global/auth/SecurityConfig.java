package travelfeeldog.global.auth;

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
import travelfeeldog.infra.oauth2.service.CustomOAuth2UserService;
import travelfeeldog.member.domain.model.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
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
                        .requestMatchers("/", "/test/**" ,"/actuator/health"
                                ,"api/v1/redis/**"
                                ,"api/v1/member/**"
                                ,"/swagger-ui/**","/usage" // for swagger
                                ).permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest()
                        .authenticated()
                )
                .logout(withDefaults())
                .oauth2Login(request -> request.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)));

        return httpSecurity.build();
    }
}
