package kr.submit.userfeature.verify.domain.service;

import kr.submit.userfeature.core.error.NotFoundException;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.repository.VerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VerifyDomainService {

    private final VerifyRepository verifyRepository;

    public boolean isVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(VerifyRequest verifyRequest) {
        return verifyRepository.findById(verifyRequest.getVerifyId())
                .orElseThrow(() -> new NotFoundException("해당하는 인증값이 존재하지 않습니다"))
                .isVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(verifyRequest.getVerifyUsage(), verifyRequest.getVerifyType(), verifyRequest.getVerifyTypeValue());
    }

    public boolean isNotVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(VerifyRequest verifyRequest) {
        return !this.isVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(verifyRequest);
    }
}