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

import java.time.*;
import java.util.Date;
import java.util.UUID;

// TODO JWT 생성
@RequiredArgsConstructor
@Component
public class JwtTokenGenerator {

    public static final String ACCESS_TOKEN_KEY = "accessToken";
    private final ConfigurableJWSMinter<SecurityContext> jwsMinter;

    public String generate(UserPrincipal userPrincipal) throws JOSEException {

        final JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .build();

        final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(userPrincipal.getUsername())
                .issueTime(new Date())
                .notBeforeTime(new Date())
                .expirationTime(Date.from(ZonedDateTime.now(ZoneId.systemDefault()).plusHours(1).toInstant()))
                .claim(UserPrincipal.Fields.nickname, userPrincipal.getNickname())
                .claim(UserPrincipal.Fields.email, userPrincipal.getEmail())
                .claim(UserPrincipal.Fields.phoneNumber, userPrincipal.getPhoneNumber())
                .claim(UserPrincipal.Fields.name, userPrincipal.getName())
                .claim(UserPrincipal.Fields.roleType, userPrincipal.getRoleType())
                .build();

        return jwsMinter.mint(jwsHeader, claimsSet.toPayload(), null).serialize();
    }
}