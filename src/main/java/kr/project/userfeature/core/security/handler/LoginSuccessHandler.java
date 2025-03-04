package kr.project.userfeature.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import kr.project.userfeature.core.security.jwt.token.JwtTokenGenerator;
import kr.project.userfeature.core.security.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JOSEException.class)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream outputStream = response.getOutputStream();

        final Object principal = authentication.getPrincipal();
        if(principal instanceof UserPrincipal) {
            final UserPrincipal userPrincipal = (UserPrincipal) principal;
            outputStream.write(objectMapper.writeValueAsBytes(Map.of(JwtTokenGenerator.ACCESS_TOKEN_KEY, jwtTokenGenerator.generate(userPrincipal))));
        }

        outputStream.flush();

    }
}