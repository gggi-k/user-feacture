package kr.submit.userfeature.verify.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
    private VerifyUsage verifyUsage;
    @JsonView
    private VerifyType verifyType;

    @NotBlank(groups = VerifyView.Verify.class)
    @JsonView(VerifyView.Verify.class)
    private String verifyTypeValue;

    @NotBlank(groups = {VerifyView.Verify.class, VerifyView.Number.class})
    @JsonView({VerifyView.Verify.class, VerifyView.Number.class})
    private String verifyNumber;

}