package kr.project.userfeature.core.security;

import kr.project.userfeature.annotation.SecurityTag;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;

@SecurityTag
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder;

    @ValueSource(strings = {"test1", "test2"})
    @ParameterizedTest(name = "패스워드 인코더 테스트")
    void encode(String value) {

        System.out.println(passwordEncoder.encode(value));
    }


}