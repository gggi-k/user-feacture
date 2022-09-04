package kr.submit.userfeature.security.config;

import kr.submit.userfeature.security.dto.UserPrincipal;
import kr.submit.userfeature.security.handler.LoginFailureHandler;
import kr.submit.userfeature.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity http,
                                           LoginSuccessHandler successHandler,
                                           LoginFailureHandler failureHandler) throws Exception {
        return http
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter(UserPrincipal.Fields.userId)
                    .passwordParameter(UserPrincipal.Fields.password)
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .permitAll()
                    .and()
                .build();
    }
}