package kr.submit.userfeature.user.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    ADMIN("관리자"),
    USER("사용자");

    private static final String ROLE_PREFIX = "ROLE_";

    private final String name;

    public String getRoleNameWithRolePrefix() {
        return ROLE_PREFIX.concat(this.name());
    }

}