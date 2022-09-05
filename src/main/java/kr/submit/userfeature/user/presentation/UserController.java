package kr.submit.userfeature.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.core.security.service.SecurityUserDetailService;
import kr.submit.userfeature.user.dto.UserResponse;
import kr.submit.userfeature.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "사용자")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final SecurityUserDetailService userDetailService;

    @Operation(summary = "사용자 조회")
    @GetMapping("/my-info")
    public UserResponse findByMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.findByUserId(principal.getUserId());
    }

    @Operation(summary = "패스워드 수정")
    @PatchMapping("/my-info/password/{password}")
    public void updatePassword(@PathVariable Long userId, @PathVariable String password, @AuthenticationPrincipal UserPrincipal principal) {
        userDetailService.updatePassword(principal, password);
    }

    @Operation(summary = "사용자 조회")
    @GetMapping("/{userId}")
    public UserResponse findByUserId(@PathVariable Long userId) {
        return userService.findByUserId(userId);
    }

    @Operation(summary = "사용자 수정")
    @PutMapping("/{userId}")
    public UserResponse update(@PathVariable Long userId) {
        return null;
    }



}