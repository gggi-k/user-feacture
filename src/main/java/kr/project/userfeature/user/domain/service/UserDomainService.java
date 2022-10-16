package kr.project.userfeature.user.domain.service;

import kr.project.userfeature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserDomainService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isDuplicateByEmail(String email) {
        return userRepository.existsByEmailAndEnabledTrue(email);
    }

    @Transactional(readOnly = true)
    public boolean isDuplicateByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumberAndEnabledTrue(phoneNumber);
    }
}