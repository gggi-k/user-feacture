package kr.submit.userfeature.verify.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(staticName = "create")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class VerifyRequest {

    @JsonView
    private Long verifyId;
    @JsonView
    private VerifyUsage verifyUsage;
    @JsonView
    private VerifyType verifyType;

    @Schema(description = "인증유형값(핸드폰번호, 이메일)", example = "01012345678")
    @NotBlank(groups = VerifyView.Verify.class)
    @JsonView(VerifyView.Verify.class)
    private String verifyTypeValue;

    @Schema(description = "인증번호", example = "123543")
    @NotBlank(groups = {VerifyView.Verify.class, VerifyView.Number.class})
    @JsonView({VerifyView.Verify.class, VerifyView.Number.class})
    private String verifyNumber;

}