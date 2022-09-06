package kr.submit.userfeature.verify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import kr.submit.userfeature.verify.domain.entity.VerifyEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder(access = AccessLevel.PRIVATE)
public class VerifyResponse {

    private final Long verifyId;
    private final VerifyUsage verifyUsage;
    private final VerifyType verifyType;
    private final String verifyTypeValue;
    private final String verifyNumber;
    private final boolean verified;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static VerifyResponse fromEntity(VerifyEntity verifyEntity) {
        return VerifyResponse.builder()
                .verifyId(verifyEntity.getVerifyId())
                .verifyUsage(verifyEntity.getVerifyUsage())
                .verifyType(verifyEntity.getVerifyType())
                .verifyTypeValue(verifyEntity.getVerifyTypeValue())
                .verifyNumber(verifyEntity.getVerifyNumber())
                .verified(verifyEntity.isVerified())
                .createdAt(verifyEntity.getCreatedAt())
                .updatedAt(verifyEntity.getUpdatedAt())
                .build();
    }
}