package kr.submit.userfeature.verify.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerifyUsage {

    SIGNUP("회원가입"),
    CREATE_USER("사용자 생성"),
    UPDATE_USER("사용자 수정"),
    MY_INFO("나의 정보 수정"),
    FORGOT_PASSWORD("패스워드 찾기"),
    FORGOT_ID("아이디 찾기");

    private final String name;
}