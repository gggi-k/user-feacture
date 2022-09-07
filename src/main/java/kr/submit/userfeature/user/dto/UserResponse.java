package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
public class UserResponse {

    @Schema(description = "사용자아이디", example = "123")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private Long userId;

    @Schema(description = "닉네임", example = "닉네임입니당")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private String nickname;

    @Schema(description = "이름", example = "나는야 홍길동")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private String name;

    @Schema(description = "이메일", example = "test@test.com")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private String email;

    @Schema(description = "핸드폰번호", example = "01900001111")
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private String phoneNumber;

    @Schema(description = "역할유형", example = "USER")
    @JsonView(UserView.List.class)
    private RoleType roleType;

    @Schema(description = "활성여부", example = "true")
    @JsonView(UserView.List.class)
    private boolean enabled;

    @Schema(description = "생성일시")
    @JsonView(UserView.List.class)
    private LocalDateTime createdAt;

    @Schema(description = "생성자", example = "0")
    @JsonView(UserView.List.class)
    private Long createdBy;

    @Schema(description = "갱신일시")
    @JsonView(UserView.List.class)
    private LocalDateTime updatedAt;

    @Schema(description = "갱신자", example = "0")
    @JsonView(UserView.List.class)
    private Long updatedBy;

    @Schema(description = "버전", example = "0")
    @JsonView
    private Long version;

    public static UserResponse fromEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .nickname(userEntity.getNickname())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .roleType(userEntity.getRoleType())
                .version(userEntity.getVersion())
                .enabled(userEntity.isEnabled())
                .createdAt(userEntity.getCreatedAt())
                .createdBy(userEntity.getCreatedBy())
                .updatedAt(userEntity.getUpdatedAt())
                .updatedBy(userEntity.getUpdatedBy())
                .build();
    }
}