package kr.project.userfeature.user.domain.entity;

import kr.project.userfeature.user.domain.code.RoleType;
import kr.project.userfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Entity
@Table(name = "USERS")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE USERS SET ENABLED = 'N' WHERE USER_ID = ?")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    @Comment("사용자아이디")
    private Long userId;

    @Column(name = "PASSWORD")
    @Comment("패스워드")
    private String password;

    @Setter
    @Column(name = "NICKNAME")
    @Comment("닉네임")
    private String nickname;

    @Setter
    @Column(name = "NAME")
    @Comment("이름")
    private String name;

    @Setter
    @Column(name = "EMAIL")
    @Comment("이메일")
    private String email;

    @Setter
    @Column(name = "PHONE_NUMBER")
    @Comment("핸드폰번호")
    private String phoneNumber;

    @Column(name = "ROLE_TYPE")
    @Comment("역할유형")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}