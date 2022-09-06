package kr.submit.userfeature.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.user.domain.code.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Pageable;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserQuery {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "핸드폰번호")
    private String phoneNumber;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "활성여부")
    private Boolean enabled;

    @Schema(description = "역할유형")
    private RoleType roleType;

    @Schema(hidden = true)
    private Pageable pageable;
}