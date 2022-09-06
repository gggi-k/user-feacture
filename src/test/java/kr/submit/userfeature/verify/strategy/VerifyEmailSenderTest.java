package kr.submit.userfeature.verify.strategy;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class VerifyEmailSenderTest {

    private final VerifyEmailSender verifyEmailSender;

    @Test
    void send() {
        verifyEmailSender.send("userfeaturemail2022@gmail.com", "42222");
    }
}