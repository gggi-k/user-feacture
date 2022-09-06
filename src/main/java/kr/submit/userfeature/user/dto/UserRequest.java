package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import kr.submit.userfeature.core.validation.annotation.EmailPattern;
import kr.submit.userfeature.core.validation.annotation.PasswordPattern;
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
import javax.validation.constraints.Pattern;

@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserRequest {

    @NotNull(groups = UserView.Update.class)
    @JsonView(UserView.Update.class)
    private Long userId;

    @PasswordPattern(groups = UserView.Create.class, regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @JsonView({UserView.Create.class, UserView.Update.class})
    private String password;

    @JsonView({UserView.Create.class, UserView.Update.class})
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    private String nickname;

    @EmailPattern(groups = {UserView.Create.class, UserView.Update.class})
    @JsonView({UserView.Create.class, UserView.Update.class})
    private String email;

    @JsonView({UserView.Create.class, UserView.Update.class})
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    @Pattern(groups = {UserView.Create.class, UserView.Update.class}, regexp = "/d{11,}")
    private String phoneNumber;

    @JsonView()
    private RoleType roleType;

    @JsonView()
    private VerifyUsage verifyUsage;

}