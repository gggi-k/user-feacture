package kr.project.userfeature.user.application;

import kr.project.userfeature.core.error.DuplicateException;
import kr.project.userfeature.core.error.NotVerifiedException;
import kr.project.userfeature.user.domain.service.UserDomainService;
import kr.project.userfeature.user.repository.UserRepository;
import kr.project.userfeature.verify.application.VerifyService;
import kr.project.userfeature.verify.domain.code.VerifyType;
import kr.project.userfeature.verify.domain.service.VerifyDomainService;
import kr.project.userfeature.verify.dto.VerifyRequest;
import kr.project.userfeature.user.domain.entity.UserEntity;
import kr.project.userfeature.user.dto.UserQuery;
import kr.project.userfeature.user.dto.UserRequest;
import kr.project.userfeature.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final VerifyService verifyService;
    private final VerifyDomainService verifyDomainService;

    @Transactional(readOnly = true)
    public Page<UserResponse> findAllByQuery(UserQuery query) {
        return new PageImpl<>(userRepository.findAllByQueryWithPaging(query), query.getPageable(), userRepository.findCountByQuery(query));
    }

    @Transactional(readOnly = true)
    public UserResponse findByUserId(Long userId) {
        return UserResponse.fromEntity(userRepository.findByUserIdAndEnabledTrue(userId).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        return UserResponse.fromEntity(userRepository.findByEmailAndEnabledTrue(email).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }

    @Transactional(readOnly = true)
    public UserResponse findByPhoneNumber(String phoneNumber) {
        return UserResponse.fromEntity(userRepository.findByPhoneNumberAndEnabledTrue(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }

    public UserResponse create(UserRequest userRequest) {

        if(userDomainService.isDuplicateByEmail(userRequest.getEmail())) throw new DuplicateException("이메일이 중복됩니다");

        if(verifyService.findByVerifyUsageAndVerifyTypeAndVerifyValue(VerifyRequest.create()
            .setVerifyUsage(userRequest.getVerifyUsage())
            .setVerifyType(VerifyType.EMAIL)
            .setVerifyTypeValue(userRequest.getEmail())).isNotVerified()) throw new NotVerifiedException("이메일이 인증이 안되었습니다");

        if(userDomainService.isDuplicateByPhoneNumber(userRequest.getPhoneNumber())) throw new DuplicateException("핸드폰번호가 중복됩니다");

        //TODO 핸드폰 인증기능이 안되서 주석처리
//        if(verifyService.findByVerifyUsageAndVerifyTypeAndVerifyValue(VerifyRequest.create()
//                .setVerifyUsage(userRequest.getVerifyUsage())
//                .setVerifyType(VerifyType.PHONE_NUMBER)
//                .setVerifyTypeValue(userRequest.getPhoneNumber())).isVerified()) throw new NotVerifiedException("핸드폰번호가 인증이 안되었습니다");

        return UserResponse.fromEntity(userRepository.save(UserEntity.builder()
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .nickname(userRequest.getNickname())
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .email(userRequest.getEmail())
                .roleType(userRequest.getRoleType())
                .build()));
    }

    public UserResponse update(UserRequest userRequest) {
        final UserEntity userEntity = userRepository.findByUserIdAndEnabledTrue(userRequest.getUserId()).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다"));

        if(!Objects.equals(userEntity.getEmail(), userRequest.getEmail())) {
            if(userDomainService.isDuplicateByEmail(userRequest.getEmail())) throw new DuplicateException("이메일이 중복됩니다");

            if(verifyDomainService.isNotVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(VerifyRequest.create()
                    .setVerifyId(userRequest.getEmailVerifyId())
                    .setVerifyUsage(userRequest.getVerifyUsage())
                    .setVerifyType(VerifyType.EMAIL)
                    .setVerifyTypeValue(userRequest.getEmail()))) throw new NotVerifiedException("이메일이 인증이 안되었습니다");
        }

        if(!Objects.equals(userEntity.getPhoneNumber(), userRequest.getPhoneNumber())) {
            if(userDomainService.isDuplicateByPhoneNumber(userRequest.getPhoneNumber())) throw new DuplicateException("핸드폰번호가 중복됩니다");

        //TODO 핸드폰 인증기능이 안되서 주석처리
//            if(verifyDomainService.isNotVerifiedOfVerifyUsageAndVerifyTypeAndVerifyTypeValue(VerifyRequest.create()
//                    .setVerifyId(userRequest.getEmailVerifyId())
//                    .setVerifyUsage(userRequest.getVerifyUsage())
//                    .setVerifyType(VerifyType.PHONE_NUMBER)
//                    .setVerifyTypeValue(userRequest.getPhoneNumber()))) throw new NotVerifiedException("핸드폰번호가 인증이 안되었습니다");
        }

        userEntity.setEmail(userRequest.getEmail());
        userEntity.setNickname(userRequest.getNickname());
        userEntity.setName(userEntity.getName());
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());

        return UserResponse.fromEntity(userRepository.save(userEntity));
    }

    public void deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
    }
}