package kr.submit.userfeature.verify.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerifyUsage {

    SIGNUP("회원가입"),
    FORGOT_PASSWORD("패스워드 찾기"),
    FORGOT_ID("아이디 찾기");

    private final String name;
}