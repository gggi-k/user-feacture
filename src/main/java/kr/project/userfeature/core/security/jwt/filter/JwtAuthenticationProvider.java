package kr.project.userfeature.core.security.jwt.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import kr.project.userfeature.core.security.jwt.token.UserDetailsJwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

// TODO JWT 확인
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    public Authentication authenticate(String jwtToken) throws AuthenticationException {
        try {
            return new UserDetailsJwtAuthenticationToken(jwtProcessor.process(jwtToken, null));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (BadJOSEException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

}