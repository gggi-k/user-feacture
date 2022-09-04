package kr.submit.userfeature.user.application.service;

import kr.submit.userfeature.user.application.dto.UserRequest;
import kr.submit.userfeature.user.application.dto.UserResponse;
import kr.submit.userfeature.user.domain.service.UserDomainService;
import kr.submit.userfeature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserService {

    private final UserDomainService userDomainService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponse findByUserId(Long userId) {
        return UserResponse.fromEntity(userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }

    public UserResponse create(UserRequest userRequest) {

        return null;
    }
}