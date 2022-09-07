package kr.submit.userfeature.verify.strategy;

import kr.submit.userfeature.verify.domain.code.VerifyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@Component(VerifyType.PHONE_NUMBER_VERIFY_BEAN)
public class VerifyPhoneNumberSender implements VerifyTypeStrategy {

    @Async
    @Override
    public void send(String verifyTypeValue, String verifyNumber) {
        log.info("발송");
    }
}