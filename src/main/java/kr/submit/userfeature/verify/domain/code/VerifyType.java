package kr.submit.userfeature.verify.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@Getter
@RequiredArgsConstructor
public enum VerifyType {

    PHONE_NUMBER("핸드폰번호", ""),
    EMAIL("이메일", VerifyType.EMAIL_VERIFY_BEAN);

    public static final String EMAIL_VERIFY_BEAN = "verifyEmailSender";

    private final String name;
    private final String verifyBean;

}