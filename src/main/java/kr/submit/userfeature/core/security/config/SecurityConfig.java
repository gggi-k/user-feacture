package kr.submit.userfeature.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.core.security.handler.LoginFailureHandler;
import kr.submit.userfeature.core.security.handler.LoginSuccessHandler;
import kr.submit.userfeature.core.security.jwt.filter.BearerJwtTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final BasicErrorController basicErrorController;
    private final ObjectMapper objectMapper;

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @Description("security config filter")
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           BearerJwtTokenAuthenticationFilter authenticationFilter,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           LoginSuccessHandler successHandler,
                                           LoginFailureHandler failureHandler) throws Exception {
        return http
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .httpBasic()
                    .disable()
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
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
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
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

    @Bean
    @Description("권한 미존재시")
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, authException);
            byte[] bytes = objectMapper.writeValueAsBytes(basicErrorController.error(request).getBody());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setContentLength(bytes.length);

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        };
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}