package kr.submit.userfeature.verify.strategy;

import kr.submit.userfeature.annotation.VerifyTag;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@VerifyTag
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class VerifyEmailSenderTest {

    private final VerifyEmailSender verifyEmailSender;

    @DisplayName("이메일 발송테스트")
    @Test
    void send() {
        verifyEmailSender.send("userfeaturemail2022@gmail.com", "42222");
    }
}