package kr.submit.userfeature.verify.repository;

import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import kr.submit.userfeature.verify.domain.entity.VerifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepository extends JpaRepository<VerifyEntity, Long> {

    Optional<VerifyEntity> findFirstByVerifyUsageAndVerifyTypeAndAndVerifyTypeValue(VerifyUsage verifyUsage, VerifyType verifyType, String verifyTypeValue);


}