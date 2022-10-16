package kr.project.userfeature.user.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.project.userfeature.verify.application.VerifyService;
import kr.project.userfeature.verify.domain.code.VerifyType;
import kr.project.userfeature.verify.domain.code.VerifyUsage;
import kr.project.userfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.project.userfeature.user.application.UserService;
import kr.project.userfeature.user.application.UserVerifyService;
import kr.project.userfeature.user.dto.UserVerifyRequest;
import kr.project.userfeature.verify.dto.VerifyRequest;
import kr.project.userfeature.verify.dto.VerifyView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@SwaggerApiResponses
@Tag(name = "아이디/비밀번호 찾기")
@RequiredArgsConstructor
@RestController
public class UserVerifyController {

    private final UserService userService;
    private final VerifyService verifyService;
    private final UserVerifyService userVerifyService;

    @Operation(summary = "아이디찾기 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-id/send/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumberForId(@Parameter(description = "핸드폰번호") @PathVariable String phoneNumber) {
        userVerifyService.sendVerifyNumberByUserVerifyRequestInCaseForgotId(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(phoneNumber)
        );
    }

    @Operation(summary = "아이디찾기 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-id/send/email/{email}")
    public void sendVerifyNumberByEmailForId(@Parameter(description = "이메일") @PathVariable String email) {
        userVerifyService.sendVerifyNumberByUserVerifyRequestInCaseForgotId(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL)
                .setVerifyTypeValue(email)
        );
    }

    @Operation(summary = "아이디찾기 핸드폰번호 인증확인")
    @PostMapping("/forgot-id/verify/phone-number")
    public Long verifyByPhoneNumberForId(
            @Validated(VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER));

        return userService.findByPhoneNumber(verifyRequest.getVerifyTypeValue()).getUserId();
    }

    @Operation(summary = "아이디찾기 이메일 인증확인")
    @PostMapping("/forgot-id/verify/email")
    public Long verifyByEmailForId(
            @Validated(VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL));
        return userService.findByEmail(verifyRequest.getVerifyTypeValue()).getUserId();
    }

    @Operation(summary = "비밀번호찾기 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-password/send/user/{userId}/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumberForPassword(
            @Parameter(description = "사용자아이디") @PathVariable Long userId,
            @Parameter(description = "핸드폰번호") @PathVariable String phoneNumber) {
        userVerifyService.sendVerifyNumberByUserVerifyRequestInCaseForgotPassword(
                UserVerifyRequest.create()
                    .setUserId(userId)
                    .setVerifyRequest(VerifyRequest.create()
                        .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                        .setVerifyType(VerifyType.PHONE_NUMBER)
                        .setVerifyTypeValue(phoneNumber)));
    }

    @Operation(summary = "비밀번호찾기 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-password/send/user/{userId}/email/{email}")
    public void sendVerifyNumberByEmailForPassword(
            @Parameter(description = "사용자아이디") @PathVariable Long userId,
            @Parameter(description = "이메일") @PathVariable String email) {
        userVerifyService.sendVerifyNumberByUserVerifyRequestInCaseForgotPassword(
                UserVerifyRequest.create()
                    .setUserId(userId)
                    .setVerifyRequest(VerifyRequest.create()
                            .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                            .setVerifyType(VerifyType.EMAIL)
                            .setVerifyTypeValue(email)));
    }

    @Operation(summary = "비밀번호찾기 핸드폰번호 인증확인")
    @PostMapping("/forgot-password/verify/user/{userId}/phone-number")
    public Long verifyByPhoneNumberForPassword(
            @Parameter(description = "사용자아이디") @PathVariable Long userId,
            @Validated(VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        return verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                .setVerifyType(VerifyType.PHONE_NUMBER)).getVerifyId();
    }

    @Operation(summary = "비밀번호찾기 이메일 인증확인")
    @PostMapping("/forgot-password/verify/user/{userId}/email")
    public Long verifyByEmailForPassword(
            @Parameter(description = "사용자아이디") @PathVariable Long userId,
            @Validated(VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        return verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                .setVerifyType(VerifyType.EMAIL)).getVerifyId();
    }

    @Operation(summary = "비밀번호찾기 핸드폰번호 인증확인 후 비밀번호 변경")
    @PatchMapping("/forgot-password/verify/phone-number/change-password")
    public void changePasswordVerifyByPhoneNumberForPassword(
            @Validated(VerifyView.Password.class)
            @JsonView(VerifyView.Password.class)
            @RequestBody UserVerifyRequest userVerifyRequest) {
        userVerifyService.changePasswordInCaseForgotPassword(
                userVerifyRequest
                    .setVerifyRequest(VerifyRequest.create()
                        .setVerifyId(userVerifyRequest.getVerifyId())
                        .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                        .setVerifyType(VerifyType.PHONE_NUMBER)
                        .setVerifyTypeValue(userVerifyRequest.getVerifyTypeValue())
                    ));
    }

    @Operation(summary = "비밀번호찾기 이메일 인증확인 후 비밀번호 변경")
    @PatchMapping("/forgot-password/verify/email/change-password")
    public void changePasswordVerifyByEmailForPassword(
            @Validated(VerifyView.Password.class)
            @JsonView(VerifyView.Password.class)
            @RequestBody UserVerifyRequest userVerifyRequest) {
        userVerifyService.changePasswordInCaseForgotPassword(
                userVerifyRequest
                    .setVerifyRequest(VerifyRequest.create()
                        .setVerifyId(userVerifyRequest.getVerifyId())
                        .setVerifyUsage(VerifyUsage.FORGOT_PASSWORD)
                        .setVerifyType(VerifyType.EMAIL)
                        .setVerifyTypeValue(userVerifyRequest.getVerifyTypeValue())
                    ));
    }
}