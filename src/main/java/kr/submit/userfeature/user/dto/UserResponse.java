package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@Builder(access = AccessLevel.PACKAGE)
public class UserResponse {

    @Schema(description = "사용자아이디")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final Long userId;

    @Schema(description = "닉네임")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String nickName;

    @Schema(description = "이름")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String name;

    @Schema(description = "이메일")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String email;

    @Schema(description = "핸드폰번호")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String phoneNumber;

    @Schema(description = "역할유형")
    @JsonView(UserView.List.class)
    private final RoleType roleType;

    @Schema(description = "활성여부")
    @JsonView(UserView.List.class)
    private final boolean enabled;

    public static UserResponse fromEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .nickName(userEntity.getNickname())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .roleType(userEntity.getRoleType())
                .build();
    }
}