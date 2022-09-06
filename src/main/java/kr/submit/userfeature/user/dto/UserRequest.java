package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.core.validation.annotation.EmailPattern;
import kr.submit.userfeature.core.validation.annotation.PasswordPattern;
import kr.submit.userfeature.core.validation.annotation.PhoneNumberPattern;
import kr.submit.userfeature.user.domain.code.RoleType;
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

    @Schema(description = "사용자아이디")
    @NotNull(groups = UserView.Update.class)
    @JsonView(UserView.Update.class)
    private Long userId;

    @Schema(description = "패스워드")
    @PasswordPattern(groups = UserView.Create.class)
    @JsonView({UserView.Create.class, UserView.Update.class})
    private String password;

    @Schema(description = "닉네임")
    @JsonView({UserView.Create.class, UserView.Update.class})
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    private String nickname;

    @Schema(description = "이름")
    @JsonView({UserView.Create.class, UserView.Update.class})
    @NotBlank(groups = {UserView.Create.class, UserView.Update.class})
    private String name;

    @Schema(description = "이메일")
    @EmailPattern(groups = {UserView.Create.class, UserView.Update.class})
    @JsonView({UserView.Create.class, UserView.Update.class})
    private String email;

    @Schema(description = "핸드폰번호")
    @JsonView({UserView.Create.class, UserView.Update.class})
    @PhoneNumberPattern(groups = {UserView.Create.class, UserView.Update.class})
    private String phoneNumber;

    @JsonView
    private RoleType roleType;

    @JsonView
    private VerifyUsage verifyUsage;

}