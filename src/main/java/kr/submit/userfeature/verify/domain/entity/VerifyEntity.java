package kr.submit.userfeature.verify.domain.entity;

import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "VERIFY",
    indexes = @Index(
        name = "idx_usage_type_number",
        columnList = "VERIFY_USAGE, VERIFY_TYPE, VERIFY_TYPE_VALUE"
    ))
@DynamicInsert
@DynamicUpdate
public class VerifyEntity {

    @Id
    @GeneratedValue
    @Comment("인증")
    @Column(name = "VERIFY_ID")
    private Long verifyId;

    @Comment("인증용도")
    @Column(name = "VERIFY_USAGE", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private VerifyUsage verifyUsage;

    @Comment("인증유형")
    @Column(name = "VERIFY_TYPE", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private VerifyType verifyType;

    @Comment("인증유형값")
    @Column(name = "VERIFY_TYPE_VALUE", updatable = false, nullable = false)
    private String verifyTypeValue;

    @Comment("인증번호")
    @Column(name = "VERIFY_NUMBER", updatable = false, nullable = false)
    private String verifyNumber;

    @Type(type = "yes_no")
    @ColumnDefault("'N'")
    @Comment("인증여부")
    @Column(name = "VERIFIED", nullable = false)
    private boolean verified;

    @CreatedDate
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Comment("등록일시")
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Comment("갱신일시")
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public boolean isAfterCreatedAtByMinutes(long minutes) {
        return this.createdAt.minusMinutes(minutes).isAfter(LocalDateTime.now());
    }

    public boolean equalsVerifyNumber(String verifyNumber) {
        return this.verifyNumber.equals(verifyNumber);
    }

    public void successVerified() {
        this.verified = true;
    }
}