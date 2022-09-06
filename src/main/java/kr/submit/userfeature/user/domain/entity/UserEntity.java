package kr.submit.userfeature.user.domain.entity;

import kr.submit.userfeature.core.jpa.entity.BaseEntity;
import kr.submit.userfeature.user.domain.code.RoleType;
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
    @Column
    @Comment("사용자아이디")
    private Long userId;

    @Column
    @Comment("패스워드")
    private String password;

    @Setter
    @Column
    @Comment("닉네임")
    private String nickName;

    @Setter
    @Column
    @Comment("이메일")
    private String email;

    @Setter
    @Column
    @Comment("핸드폰번호")
    private String phoneNumber;

    @Column
    @Comment("역할유형")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}