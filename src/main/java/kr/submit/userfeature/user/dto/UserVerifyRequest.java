package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.core.validation.annotation.PasswordPattern;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.dto.VerifyView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Getter
@Setter
@ToString
public class UserVerifyRequest {

    @Schema(description = "사용자아이디", example = "123")
    @NotNull(groups = VerifyView.Password.class)
    @JsonView(VerifyView.Password.class)
    private Long userId;

    @Schema(description = "패스워드", example = "dfgdf1234")
    @PasswordPattern(groups = VerifyView.Password.class)
    @JsonView(VerifyView.Password.class)
    private String password;

    @Schema(description = "인증아이디", example = "1")
    @NotNull(groups = VerifyView.Password.class)
    @JsonView(VerifyView.Password.class)
    private Long verifyId;

    @Schema(description = "인증유형값", example = "01042353334")
    @NotBlank(groups = VerifyView.Password.class)
    @JsonView(VerifyView.Password.class)
    private String verifyTypeValue;

    @JsonView
    private VerifyRequest verifyRequest;
}