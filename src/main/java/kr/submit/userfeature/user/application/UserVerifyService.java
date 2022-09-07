package kr.submit.userfeature.user.application;

import kr.submit.userfeature.core.error.NotFoundException;
import kr.submit.userfeature.core.error.NotVerifiedException;
import kr.submit.userfeature.core.security.service.SecurityUserDetailService;
import kr.submit.userfeature.user.dto.UserResponse;
import kr.submit.userfeature.user.dto.UserVerifyRequest;
import kr.submit.userfeature.verify.application.VerifyService;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.service.VerifyDomainService;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.dto.VerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@Service
public class UserVerifyService {

    private final VerifyService verifyService;
    private final VerifyDomainService verifyDomainService;
    private final UserService userService;
    private final SecurityUserDetailService userDetailService;

    public void sendVerifyNumberByUserVerifyRequestInCaseForgotPassword(UserVerifyRequest userVerifyRequest) {
        final UserResponse userResponse = userService.findByUserId(userVerifyRequest.getUserId());
        final VerifyRequest verifyRequest = userVerifyRequest.getVerifyRequest();
        if(isNotSameVerifyTypeValue(verifyRequest.getVerifyType(), userResponse, verifyRequest.getVerifyTypeValue())) throw new NotFoundException("존재하지 않는 사용자입니다");

        verifyService.sendVerifyNumberByVerifyTypeValue(verifyRequest);
    }

    public VerifyResponse verifyNumberInCaseForgotPassword(UserVerifyRequest userVerifyRequest) {
        final UserResponse userResponse = userService.findByUserId(userVerifyRequest.getUserId());
        final VerifyRequest verifyRequest = userVerifyRequest.getVerifyRequest();
        if(isNotSameVerifyTypeValue(verifyRequest.getVerifyType(), userResponse, verifyRequest.getVerifyTypeValue())) throw new NotFoundException("존재하지 않는 사용자입니다");

        return verifyService.verifyNumber(verifyRequest);
    }

    public void changePasswordInCaseForgotPassword(UserVerifyRequest userVerifyRequest) {
        UserDetails userDetails = userDetailService.loadUserByUsername(String.valueOf(userVerifyRequest.getUserId()));

        if(verifyDomainService.isNotVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(userVerifyRequest.getVerifyRequest())) throw new NotVerifiedException("인증이 안되었습니다");

        userDetailService.updatePassword(userDetails, userVerifyRequest.getPassword());
    }

    private boolean isNotSameVerifyTypeValue(VerifyType verifyType, UserResponse userResponse, String verifyTypeValue) {
        switch (verifyType) {
            case EMAIL: if(Objects.equals(userResponse.getEmail(), verifyTypeValue)) return false;
            case PHONE_NUMBER: if(Objects.equals(userResponse.getPhoneNumber(), verifyTypeValue)) return false;
        }
        return true;
    }
}