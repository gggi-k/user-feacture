package kr.submit.userfeature.user.application.dto;

import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(access = AccessLevel.PACKAGE)
public class UserResponse {

    private final Long userId;
    private final String nickName;
    private final String email;
    private final String phoneNumber;
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