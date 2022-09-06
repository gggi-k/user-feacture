package kr.submit.userfeature.core.security;

import kr.submit.userfeature.annotation.SecurityTag;
import kr.submit.userfeature.core.security.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;

@SecurityTag
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder;

    @ValueSource(strings = {"user", "admin"})
    @ParameterizedTest(name = "패스워드 인코더 테스트")
    void encode(String value) {

        System.out.println(passwordEncoder.encode(value));
    }


}