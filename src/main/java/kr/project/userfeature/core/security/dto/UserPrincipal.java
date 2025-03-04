package kr.project.userfeature.core.security.dto;

import com.nimbusds.jwt.JWTClaimsSet;
import kr.project.userfeature.user.domain.code.RoleType;
import kr.project.userfeature.user.domain.entity.UserEntity;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
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
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final boolean enabled;
    private final RoleType roleType;
    @Singular
    private final Set<GrantedAuthority> authorities;

    public static UserPrincipal fromEntity(UserEntity userEntity) {
        final RoleType roleType = userEntity.getRoleType();
        return UserPrincipal.builder()
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .enabled(userEntity.isEnabled())
                .roleType(roleType)
                .authorities(fromRoleType(roleType))
                .build();
    }

    public static UserPrincipal fromJwtClaimSet(JWTClaimsSet claimsSet) throws ParseException {
        final RoleType roleType = RoleType.valueOf(claimsSet.getStringClaim(Fields.roleType));
        return UserPrincipal.builder()
                .userId(Long.valueOf(claimsSet.getSubject()))
                .nickname(claimsSet.getStringClaim(Fields.nickname))
                .name(claimsSet.getStringClaim(Fields.name))
                .email(claimsSet.getStringClaim(Fields.email))
                .phoneNumber(claimsSet.getStringClaim(Fields.phoneNumber))
                .roleType(roleType)
                .authorities(fromRoleType(roleType))
                .build();
    }

    public static Set<GrantedAuthority> fromRoleType(RoleType roleType) {
        return Set.of(new SimpleGrantedAuthority(roleType.getRoleNameWithRolePrefix()));
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