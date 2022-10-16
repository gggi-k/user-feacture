package kr.project.userfeature.core.security.jwt.token;

import com.nimbusds.jwt.JWTClaimsSet;
import kr.project.userfeature.user.domain.code.RoleType;
import kr.project.userfeature.core.security.dto.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;

public class UserDetailsJwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails userDetails;

    public UserDetailsJwtAuthenticationToken(JWTClaimsSet claimsSet) throws ParseException {
        super(UserPrincipal.fromRoleType(RoleType.valueOf(claimsSet.getStringClaim(UserPrincipal.Fields.roleType))));
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