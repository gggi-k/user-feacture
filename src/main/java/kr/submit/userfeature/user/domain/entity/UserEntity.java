package kr.submit.userfeature.user.domain.entity;

import kr.submit.userfeature.user.domain.code.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "USERS")
@DynamicInsert
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue
    @Column
    @Comment("사용자아이디")
    private Long userId;

    @Column
    @Comment("패스워드")
    private String password;

    @Column
    @Comment("닉네임")
    private String nickName;

    @Column
    @Comment("이메일")
    private String email;

    @Column
    @Comment("핸드폰번호")
    private String phoneNumber;

    @Column
    @Comment("역할유형")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder.Default
    @Type(type = "yes_no")
    @Column
    @ColumnDefault("'Y'")
    @Comment("활성여부")
    private boolean enabled = true;
}