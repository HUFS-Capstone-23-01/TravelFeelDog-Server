package travelfeeldog.global.secure.auth;

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
import travelfeeldog.domain.member.domain.model.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
//    private final JWTProvider jwtProvider;
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
                        .requestMatchers("/",
                                "/swagger-ui/**","/usage","/test/**"
                                ,"/actuator/health").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .logout(withDefaults())
                .oauth2Login(request -> request
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));

        return httpSecurity.build();
    }
}
//
//
//public class SpringSecurityConfig {
//    //    private final JWTProvider jwtProvider;
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(
//                        AbstractHttpConfigurer::disable
//                ).cors(
//                        AbstractHttpConfigurer::disable
//                )
//                .authorizeHttpRequests(request -> request
//                                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                                .requestMatchers("/status", "/images/**", "/view/join", "/auth/join","/api/v1",
//                                        "/swagger-ui/**","/usage","/test/**"
//                                        ,"/actuator/health").permitAll()
////                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
//                                .anyRequest().authenticated()
//                )
////                .formLogin(login -> login
//////                        .loginPage("/view/login") add  when custom login // custom login page 를 이용하는 경우
//////                        .loginProcessingUrl("/login-process")
//////                        .usernameParameter("userid")
//////                        .passwordParameter("pw")
////                        .defaultSuccessUrl("/view/dashboard", true)
////                        .permitAll()
////                )
//                .logout(withDefaults())
//                .oauth2Login(request -> request
//                        .userInfoEndpoint(userInfoEndpointConfig ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService)));
//
//        return httpSecurity.build();
//    }
//
//}