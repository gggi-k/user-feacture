package kr.submit.userfeature.core.security.service;

import kr.submit.userfeature.core.error.BadRequestException;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SecurityUserDetailService implements UserDetailsService, UserDetailsPasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        final UserDetails userDetails = this.loadUserByUsername(user.getUsername());
        if(passwordEncoder.matches(userDetails.getPassword(), newPassword)) throw new BadRequestException("같은 비밀번호로 변경이 불가능합니다");
        userRepository.changePassword(Long.valueOf(user.getUsername()), passwordEncoder.encode(newPassword));
        return userDetails;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.fromEntity(userRepository.findByUserIdAndEnabledTrue(Long.valueOf(username)).orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자가 존재하지않습니다")));
    }
}