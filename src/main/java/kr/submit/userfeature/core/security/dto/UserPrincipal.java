package kr.submit.userfeature.core.security.dto;

import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@FieldNameConstants
@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
public class UserPrincipal implements UserDetails, CredentialsContainer {

    private final Long userId;
    private String password;
    private final String nickname;
    private final String email;
    private final String phoneNumber;
    private final boolean enabled;
    private final RoleType roleType;
    @Singular
    private final Set<GrantedAuthority> authorities;

    public static UserPrincipal fromEntity(UserEntity userEntity) {
        return UserPrincipal.builder()
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .enabled(userEntity.isEnabled())
                .roleType(userEntity.getRoleType())
                .authority(new SimpleGrantedAuthority(userEntity.getRoleType().getRoleNameWithRolePrefix()))
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}