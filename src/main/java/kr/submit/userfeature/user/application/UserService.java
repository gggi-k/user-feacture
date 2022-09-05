package kr.submit.userfeature.user.application;

import kr.submit.userfeature.core.error.DuplicateException;
import kr.submit.userfeature.core.error.NotVerifiedException;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import kr.submit.userfeature.user.domain.service.UserDomainService;
import kr.submit.userfeature.user.dto.UserRequest;
import kr.submit.userfeature.user.dto.UserResponse;
import kr.submit.userfeature.user.repository.UserRepository;
import kr.submit.userfeature.verify.application.VerifyService;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final VerifyService verifyService;

    @Transactional(readOnly = true)
    public UserResponse findByUserId(Long userId) {
        return UserResponse.fromEntity(userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }

    public UserResponse create(UserRequest userRequest) {

        if(userDomainService.isDuplicateByEmail(userRequest.getEmail())) throw new DuplicateException("이메일이 중복됩니다");

        if(verifyService.findByVerifyUsageAndVerifyTypeAndVerifyValue(VerifyRequest.create()
            .setVerifyUsage(userRequest.getVerifyUsage())
            .setVerifyType(VerifyType.EMAIL)
            .setVerifyTypeValue(userRequest.getEmail())).isVerified()) throw new NotVerifiedException("이메일이 인증이 안되었습니다");

        if(userDomainService.isDuplicateByPhoneNumber(userRequest.getPhoneNumber())) throw new DuplicateException("핸드폰번호가 중복됩니다");

        if(verifyService.findByVerifyUsageAndVerifyTypeAndVerifyValue(VerifyRequest.create()
                .setVerifyUsage(userRequest.getVerifyUsage())
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(userRequest.getEmail())).isVerified()) throw new NotVerifiedException("핸드폰번호가 인증이 안되었습니다");

        return UserResponse.fromEntity(userRepository.save(UserEntity.builder()
                .userId(userRequest.getUserId())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .nickName(userRequest.getNickName())
                .phoneNumber(userRequest.getPhoneNumber())
                .email(userRequest.getEmail())
                .roleType(userRequest.getRoleType())
                .build()));
    }
}