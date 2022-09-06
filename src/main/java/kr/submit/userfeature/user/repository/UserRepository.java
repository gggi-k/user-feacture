package kr.submit.userfeature.user.repository;

import kr.submit.userfeature.core.jpa.template.PagingRepositoryTemplate;
import kr.submit.userfeature.user.domain.entity.UserEntity;
import kr.submit.userfeature.user.dto.UserQuery;
import kr.submit.userfeature.user.dto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, PagingRepositoryTemplate<UserResponse, UserQuery> {

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}