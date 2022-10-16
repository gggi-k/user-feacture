package kr.project.userfeature.verify.repository;

import kr.project.userfeature.verify.domain.code.VerifyType;
import kr.project.userfeature.verify.domain.code.VerifyUsage;
import kr.project.userfeature.verify.domain.entity.VerifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepository extends JpaRepository<VerifyEntity, Long> {

    Optional<VerifyEntity> findFirstByVerifyUsageAndVerifyTypeAndAndVerifyTypeValueOrderByVerifyIdDesc(VerifyUsage verifyUsage, VerifyType verifyType, String verifyTypeValue);


}