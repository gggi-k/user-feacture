package kr.submit.userfeature.verify.dto;

import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor(staticName = "create")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class VerifyRequest {

    private VerifyUsage verifyUsage;
    private VerifyType verifyType;
    private String verifyTypeValue;
    private String verifyNumber;

}