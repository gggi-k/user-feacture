package kr.submit.userfeature.user.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.userfeature.core.security.annotation.IsAuthenticated;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.core.security.service.SecurityUserDetailService;
import kr.submit.userfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.submit.userfeature.user.application.UserService;
import kr.submit.userfeature.user.dto.UserRequest;
import kr.submit.userfeature.user.dto.UserResponse;
import kr.submit.userfeature.user.dto.UserView;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@IsAuthenticated
@SwaggerApiResponses
@Tag(name = "나의 정보")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-info")
public class MyInfoController {

    private final UserService userService;
    private final SecurityUserDetailService userDetailService;

    @Operation(summary = "나의정보 조회")
    @GetMapping
    public UserResponse findByMyInfo(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.findByUserId(principal.getUserId());
    }

    @Operation(summary = "패스워드 수정")
    @PatchMapping("/password/{password}")
    public void updatePassword(
            @Parameter(description = "패스워드")
            @PathVariable String password, @AuthenticationPrincipal UserPrincipal principal) {
        userDetailService.updatePassword(principal, password);
    }

    @Operation(summary = "나의정보 수정")
    @PutMapping
    public UserResponse update(
            @Validated(UserView.Update.class)
            @JsonView(UserView.Update.class)
            @RequestBody UserRequest userRequest, @AuthenticationPrincipal UserPrincipal principal) {
        return userService.update(userRequest.setUserId(principal.getUserId())
                .setVerifyUsage(VerifyUsage.MY_INFO));
    }
}