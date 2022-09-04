package kr.submit.userfeature.user.presentation.controller;

import kr.submit.userfeature.security.dto.UserPrincipal;
import kr.submit.userfeature.user.application.dto.UserResponse;
import kr.submit.userfeature.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/my-info")
    public UserResponse findByMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.findByUserId(principal.getUserId());
    }

    @GetMapping("/{userId}")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findByUserId(userId);
    }
}