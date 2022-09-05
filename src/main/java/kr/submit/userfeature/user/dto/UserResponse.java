package kr.submit.userfeature.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import kr.submit.userfeature.user.presentation.UserView;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
public class UserResponse {

    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final Long userId;
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String nickName;
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String email;
    @JsonView({UserView.List.class, UserView.MyInfo.class})
    private final String phoneNumber;
    @JsonView(UserView.List.class)
    private final RoleType roleType;

    public static UserResponse fromEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .nickName(userEntity.getNickName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .roleType(userEntity.getRoleType())
                .build();
    }
}