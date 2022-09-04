package kr.submit.userfeature.user.application.dto;

import kr.submit.userfeature.user.domain.code.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserRequest {

    private Long userId;
    private String password;
    private String nickName;
    private String email;
    private String phoneNumber;
    private RoleType roleType;
}