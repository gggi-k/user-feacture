package kr.submit.userfeature.core.jpa.audit;

import kr.submit.userfeature.core.security.dto.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@EnableJpaAuditing
@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserPrincipal) {
                return Optional.of(((UserPrincipal) principal).getUserId());
            }
        }

        return Optional.empty();
    }
}