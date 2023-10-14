package travelfeeldog.global.auth.secure;

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
import travelfeeldog.global.auth.jwt.filter.JwtFilter;
import travelfeeldog.global.auth.jwt.service.JwtService;
import travelfeeldog.infra.oauth2.service.CustomOAuth2UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(
                        AbstractHttpConfigurer::disable
                ).cors(
                        AbstractHttpConfigurer::disable
                )
                .logout(withDefaults())
                .oauth2Login(request -> request.userInfoEndpoint(
                        userInfoEndpointConfig -> userInfoEndpointConfig.userService(
                                customOAuth2UserService)));
        httpSecurity.addFilterAfter(new JwtFilter(jwtService), LogoutFilter.class);

        return httpSecurity.build();
    }
}
