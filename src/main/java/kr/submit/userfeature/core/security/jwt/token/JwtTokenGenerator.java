package kr.submit.userfeature.core.security.jwt.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.mint.ConfigurableJWSMinter;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenGenerator {

    private final ConfigurableJWSMinter<SecurityContext> jwsMinter;

    public String generate(UserPrincipal userPrincipal) throws JOSEException {

        final JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userPrincipal.getUsername())
                .issueTime(new Date())
                .expirationTime(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC)))
                .claim(UserPrincipal.Fields.nickname, userPrincipal.getNickname())
                .claim(UserPrincipal.Fields.email, userPrincipal.getEmail())
                .claim(UserPrincipal.Fields.phoneNumber, userPrincipal.getPhoneNumber())
                .claim(UserPrincipal.Fields.name, userPrincipal.getName())
                .claim(UserPrincipal.Fields.roleType, userPrincipal.getRoleType())
                .build();

        return jwsMinter.mint(jwsHeader, claimsSet.toPayload(), null).serialize();
    }
}