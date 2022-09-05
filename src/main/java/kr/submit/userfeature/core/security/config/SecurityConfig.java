package kr.submit.userfeature.core.security.config;

import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.core.security.handler.LoginFailureHandler;
import kr.submit.userfeature.core.security.handler.LoginSuccessHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @Description("security config filter")
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           LoginSuccessHandler successHandler,
                                           LoginFailureHandler failureHandler) throws Exception {
        return http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .httpBasic()
                    .disable()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter(UserPrincipal.Fields.userId)
                    .passwordParameter(UserPrincipal.Fields.password)
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .anyRequest().permitAll()
                .and()
                .build();
    }

    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_SUPPORT)
    @Description("비밀번호 전용 인코더")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}