package kr.submit.userfeature.user.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.userfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.submit.userfeature.verify.application.VerifyService;
import kr.submit.userfeature.verify.domain.code.VerifyType;
import kr.submit.userfeature.verify.domain.code.VerifyUsage;
import kr.submit.userfeature.verify.dto.VerifyRequest;
import kr.submit.userfeature.verify.dto.VerifyView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@SwaggerApiResponses
@Tag(name = "아이디/비밀번호 찾기")
@RequiredArgsConstructor
@RestController
public class UserVerifyController {

    private final VerifyService verifyService;

    @Operation(summary = "아이디찾기 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-id/send/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumberForId(@PathVariable String phoneNumber) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(phoneNumber)
        );
    }

    @Operation(summary = "아이디찾기 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-id/send/email/{email}")
    public void sendVerifyNumberByEmailForId(@PathVariable String email) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL)
                .setVerifyTypeValue(email)
        );
    }

    @Operation(summary = "아이디찾기 핸드폰번호 인증확인")
    @PostMapping("/forgot-id/verify/phone-number")
    public void verifyByPhoneNumberForId(
            @NotBlank(groups = VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER));
    }

    @Operation(summary = "아이디찾기 이메일 인증확인")
    @PostMapping("/forgot-id/verify/email")
    public void verifyByEmailForId(
            @NotBlank(groups = VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL));
    }

    @Operation(summary = "비밀번호찾기 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-password/send/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumberForPassword(@PathVariable String phoneNumber) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(phoneNumber)
        );
    }

    @Operation(summary = "비밀번호찾기 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/forgot-password/send/email/{email}")
    public void sendVerifyNumberByEmailForPassword(@PathVariable String email) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL)
                .setVerifyTypeValue(email)
        );
    }

    @Operation(summary = "비밀번호찾기 핸드폰번호 인증확인")
    @PostMapping("/forgot-password/verify/phone-number")
    public void verifyByPhoneNumberForPassword(
            @NotBlank(groups = VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.PHONE_NUMBER));
    }

    @Operation(summary = "비밀번호찾기 이메일 인증확인")
    @PostMapping("/forgot-password/verify/email")
    public void verifyByEmailForPassword(
            @NotBlank(groups = VerifyView.Verify.class)
            @JsonView(VerifyView.Verify.class)
            @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(VerifyUsage.FORGOT_ID)
                .setVerifyType(VerifyType.EMAIL));
    }
}