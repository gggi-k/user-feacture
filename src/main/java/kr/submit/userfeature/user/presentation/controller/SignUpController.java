package kr.submit.userfeature.user.presentation.controller;

import kr.submit.userfeature.user.application.dto.UserRequest;
import kr.submit.userfeature.user.application.dto.UserResponse;
import kr.submit.userfeature.user.application.service.UserService;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.presentation.view.UserView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final UserService userService;

    @PostMapping("sign-up")
    public UserResponse signUp(@Validated(UserView.Create.class) UserRequest userRequest) {
        return userService.create(userRequest.setRoleType(RoleType.USER));
    }

}