package kr.submit.userfeature.user.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.userfeature.core.error.DuplicateException;
import kr.submit.userfeature.core.security.annotation.IsAuthenticated;
import kr.submit.userfeature.core.security.dto.UserPrincipal;
import kr.submit.userfeature.core.security.jwt.token.JwtTokenGenerator;
import kr.submit.userfeature.core.security.service.SecurityUserDetailService;
import kr.submit.userfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.submit.userfeature.user.application.UserService;
import kr.submit.userfeature.user.domain.service.UserDomainService;
import kr.submit.userfeature.user.dto.UserRequest;
import kr.submit.userfeature.user.dto.UserResponse;
import kr.submit.userfeature.user.dto.UserView;
import kr.submit.userfeature.verify.application.VerifyService;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.dto.VerifyView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@IsAuthenticated
@SwaggerApiResponses
@Tag(name = "나의 정보")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my-info")
public class MyInfoController {

    private final UserService userService;
    private final UserDomainService userDomainService;
    private final SecurityUserDetailService userDetailService;
    private final VerifyService verifyService;

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
            @Validated(UserView.MyInfo.class)
            @JsonView(UserView.MyInfo.class)
            @RequestBody UserRequest userRequest, @AuthenticationPrincipal UserPrincipal principal) {
        return userService.update(userRequest.setUserId(principal.getUserId())
                .setVerifyUsage(VerifyUsage.MY_INFO));
    }

    @Operation(summary = "나의정보 - 핸드폰 번호 중복확인")
    @GetMapping("/duplicate/phone-number/{phoneNumber}")
    public void duplicateByPhoneNumber(@Parameter(description = "핸드폰번호") @PathVariable String phoneNumber, @AuthenticationPrincipal UserPrincipal principal) {
        if(!Objects.equals(principal.getPhoneNumber(), phoneNumber)
            && userDomainService.isDuplicateByPhoneNumber(phoneNumber)) throw new DuplicateException("핸드폰번호가 중복됩니다");
    }

    @Operation(summary = "나의정보 - 이메일 중복확인")
    @GetMapping("/duplicate/email/{email}")
    public void duplicateByEmail(@Parameter(description = "이메일") @PathVariable String email, @AuthenticationPrincipal UserPrincipal principal) {
        if(!Objects.equals(principal.getEmail(), email)
            && userDomainService.isDuplicateByEmail(email)) throw new DuplicateException("이메일이 중복됩니다");
    }

    @Operation(summary = "나의정보 - 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumber(@Parameter(description = "핸드폰번호") @PathVariable String phoneNumber) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.MY_INFO)
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(phoneNumber)
        );
    }

    @Operation(summary = "나의정보 - 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send/email/{email}")
    public void sendVerifyNumberByEmail(@Parameter(description = "이메일") @PathVariable String email) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.MY_INFO)
                .setVerifyType(VerifyType.EMAIL)
                .setVerifyTypeValue(email)
        );
    }

    @Operation(summary = "나의정보 - 핸드폰번호 인증확인")
    @PostMapping("/verify/phone-number")
    public Long verifyByPhoneNumber(@Validated(VerifyView.Verify.class)
                                    @JsonView(VerifyView.Verify.class)
                                    @RequestBody VerifyRequest verifyRequest) {
        return verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.MY_INFO)
                .setVerifyType(VerifyType.PHONE_NUMBER))
                .getVerifyId();
    }

    @Operation(summary = "나의정보 - 이메일 인증확인")
    @PostMapping("/verify/email")
    public Long verifyByEmail(@Validated(VerifyView.Verify.class)
                              @JsonView(VerifyView.Verify.class)
                              @RequestBody VerifyRequest verifyRequest) {
        return verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.MY_INFO)
                .setVerifyType(VerifyType.EMAIL))
                .getVerifyId();
    }
}