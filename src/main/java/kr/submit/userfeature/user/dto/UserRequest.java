package kr.submit.userfeature.user.dto;

import kr.submit.userfeature.core.validation.annotation.EmailPattern;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.presentation.UserView;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserRequest {

    @NotNull(groups = UserView.Update.class)
    private Long userId;

    @NotNull(groups = {UserView.Create.class, UserView.Update.class})
    private String password;

    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    private String nickName;

    @EmailPattern(groups = {UserView.Create.class, UserView.Update.class})
    private String email;

    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    private String phoneNumber;
    private RoleType roleType;

    private VerifyUsage verifyUsage;

}