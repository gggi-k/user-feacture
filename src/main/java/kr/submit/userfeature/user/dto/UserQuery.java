package kr.submit.userfeature.user.dto;

import kr.submit.userfeature.user.domain.code.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserQuery {

    private String email;
    private String phoneNumber;
    private String nickname;
    private String name;
    private Boolean enabled;
    private RoleType roleType;
    private Pageable pageable;
}