package kr.submit.userfeature.core.security.jwt.token;

import com.nimbusds.jwt.JWTClaimsSet;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.Collections;

public class UserDetailsJwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails userDetails;

    public UserDetailsJwtAuthenticationToken(JWTClaimsSet claimsSet) throws ParseException {
        super(Collections.emptyList());
        this.setAuthenticated(true);
        this.userDetails = UserPrincipal.fromJwtClaimSet(claimsSet);
    }

    @Override
    public Object getCredentials() {
        return this.userDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

}