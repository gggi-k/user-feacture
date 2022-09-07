package kr.submit.userfeature.verify.application;

import kr.submit.userfeature.core.error.BadRequestException;
import kr.submit.userfeature.core.error.NotFoundException;
import kr.submit.userfeature.core.error.TimeOutException;
import kr.submit.userfeature.core.error.ServerErrorException;
import kr.submit.userfeature.verify.domain.entity.VerifyEntity;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.dto.VerifyResponse;
import kr.submit.userfeature.verify.repository.VerifyRepository;
import kr.submit.userfeature.verify.strategy.VerifyTypeStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class VerifyService {

    private final VerifyRepository verifyRepository;
    private final Map<String, VerifyTypeStrategy> verifyTypeStrategyMap;

    @Transactional(readOnly = true)
    public VerifyResponse findById(final Long verifyId) {
        return VerifyResponse.fromEntity(verifyRepository.findById(verifyId).orElseThrow(() -> new NotFoundException("해당하는 인증값이 존재하지 않습니다")));
    }

    @Transactional(readOnly = true)
    public VerifyResponse findByVerifyUsageAndVerifyTypeAndVerifyValue(final VerifyRequest verifyRequest) {
        return VerifyResponse.fromEntity(verifyRepository.findFirstByVerifyUsageAndVerifyTypeAndAndVerifyTypeValueOrderByVerifyIdDesc(verifyRequest.getVerifyUsage(), verifyRequest.getVerifyType(), verifyRequest.getVerifyTypeValue())
                .orElseThrow(() -> new NotFoundException("해당하는 인증값이 존재하지 않습니다")));
    }

    public VerifyResponse create(VerifyRequest verifyRequest) {
        return VerifyResponse.fromEntity(verifyRepository.save(VerifyEntity.builder()
                .verifyUsage(verifyRequest.getVerifyUsage())
                .verifyType(verifyRequest.getVerifyType())
                .verifyTypeValue(verifyRequest.getVerifyTypeValue())
                .verifyNumber(verifyRequest.getVerifyNumber())
                .build()));
    }

    public VerifyResponse sendVerifyNumberByVerifyTypeValue(VerifyRequest verifyRequest) {
        try {
            verifyRequest.setVerifyNumber(String.valueOf(Math.abs(SecureRandom.getInstanceStrong().nextInt() % 1000000)));
        } catch (NoSuchAlgorithmException e) {
            throw new ServerErrorException("인증번호 생성중 에러가 발생하였습니다", e);
        }


        verifyTypeStrategyMap.getOrDefault(verifyRequest.getVerifyType().getVerifyBean(), new VerifyTypeStrategy.DefaultVerifySender())
            .send(verifyRequest.getVerifyTypeValue(), verifyRequest.getVerifyNumber());

        return this.create(verifyRequest);
    }

    public VerifyResponse verifyNumber(final VerifyRequest verifyRequest) {
        final VerifyEntity verifyEntity = verifyRepository.findFirstByVerifyUsageAndVerifyTypeAndAndVerifyTypeValueOrderByVerifyIdDesc(verifyRequest.getVerifyUsage(), verifyRequest.getVerifyType(), verifyRequest.getVerifyTypeValue())
                .orElseThrow(() -> new NotFoundException("해당하는 인증값이 존재하지 않습니다"));

        if(verifyEntity.isAfterCreatedAtByMinutes(3)) throw new TimeOutException("3분이 초과되었습니다");

        if(verifyEntity.notEqualsVerifyNumber(verifyRequest.getVerifyNumber())) throw new BadRequestException("인증번호가 잘못되었습니다");

        verifyEntity.successVerified();

        return VerifyResponse.fromEntity(verifyRepository.save(verifyEntity));
    }

}