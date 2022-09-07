package kr.submit.userfeature.core.security.config;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.mint.ConfigurableJWSMinter;
import com.nimbusds.jose.mint.DefaultJWSMinter;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws JOSEException {

        final JWK rsaJWK = new RSAKeyGenerator(RSAKeyGenerator.MIN_KEY_SIZE_BITS)
                .keyID("user-feature-jwk-id")
                .keyUse(KeyUse.SIGNATURE)
                .generate();

        return new ImmutableJWKSet<>(new JWKSet(rsaJWK));
    }

    @Bean
    public ConfigurableJWSMinter<SecurityContext> jwsMinter(JWKSource<SecurityContext> jwkSource) {
        DefaultJWSMinter<SecurityContext> jwsMinter = new DefaultJWSMinter<>();
        jwsMinter.setJWKSource(jwkSource);
        return jwsMinter;
    }

    @Bean
    public ConfigurableJWTProcessor<SecurityContext> jwtProcessor(JWKSource<SecurityContext> jwkSource) {
        DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource));
//        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<>());
        return jwtProcessor;
    }
}