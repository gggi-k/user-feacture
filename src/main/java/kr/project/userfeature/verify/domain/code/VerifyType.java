package kr.project.userfeature.verify.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerifyType {

    PHONE_NUMBER("핸드폰번호", VerifyType.PHONE_NUMBER_VERIFY_BEAN),
    EMAIL("이메일", VerifyType.EMAIL_VERIFY_BEAN);

    // TODO 전략패턴에 따른 빈 네임 enum으로 구분
    public static final String PHONE_NUMBER_VERIFY_BEAN = "verifyPhoneNumberSender", EMAIL_VERIFY_BEAN = "verifyEmailSender";

    private final String name;
    private final String verifyBean;

}