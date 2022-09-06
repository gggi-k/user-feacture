package kr.submit.userfeature.admin.presentation;


import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.userfeature.core.error.DuplicateException;
import kr.submit.userfeature.core.security.annotation.IsAdmin;
import kr.submit.userfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.submit.userfeature.user.application.UserService;
import kr.submit.userfeature.user.domain.code.RoleType;
import kr.submit.userfeature.user.domain.service.UserDomainService;
import kr.submit.userfeature.user.dto.UserQuery;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@IsAdmin
@SwaggerApiResponses
@Tag(name = "관리자 - 사용자")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final UserService userService;
    private final VerifyService verifyService;
    private final UserDomainService userDomainService;

    @Operation(summary = "관리자 - 사용자 목록 조회")
    @GetMapping
    public Page<UserResponse> findAllByQuery(final UserQuery userQuery, @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return userService.findAllByQuery(userQuery.setPageable(pageable));
    }

    @Operation(summary = "관리자 - 사용자 조회")
    @GetMapping("/{userId}")
    public UserResponse findByUserId(@Parameter(description = "사용자아이디") @PathVariable Long userId) {
        return userService.findByUserId(userId);
    }

    @Operation(summary = "관리자 - 사용자 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse create(@Validated(UserView.Create.class) @JsonView(UserView.Create.class) @RequestBody UserRequest userRequest) {
        return userService.create(userRequest.setVerifyUsage(VerifyUsage.CREATE_USER));
    }

    @Operation(summary = "관리자 - 사용자 수정")
    @PutMapping("/{userId}")
    public UserResponse update(
                                @Parameter(description = "사용자아이디")
                                @PathVariable Long userId,
                               @Validated(UserView.Update.class) @JsonView(UserView.Update.class) @RequestBody UserRequest userRequest) {
        return userService.update(userRequest.setUserId(userId)
                .setVerifyUsage(VerifyUsage.UPDATE_USER)
                .setRoleType(RoleType.USER)
        );
    }

    @Operation(summary = "관리자 - 사용자 삭제")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@Parameter(description = "사용자아이디") @PathVariable Long userId) {
        userService.deleteByUserId(userId);
    }

    @Operation(summary = "관리자 - 핸드폰 번호 중복확인")
    @GetMapping("/duplicate/phone-number/{phoneNumber}")
    public void duplicateByPhoneNumber(@Parameter(description = "핸드폰번호") @PathVariable String phoneNumber) {
        if(userDomainService.isDuplicateByPhoneNumber(phoneNumber)) throw new DuplicateException("핸드폰번호가 중복됩니다");
    }

    @Operation(summary = "관리자 - 이메일 중복확인")
    @GetMapping("/duplicate/email/{email}")
    public void duplicateByEmail(@Parameter(description = "이메일") @PathVariable String email) {
        if(userDomainService.isDuplicateByEmail(email)) throw new DuplicateException("이메일이 중복됩니다");
    }

    @Operation(summary = "관리자 - 핸드폰번호 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send/{verifyUsage:UPDATE_USER|CREATE_USER}/phone-number/{phoneNumber}")
    public void sendVerifyNumberByPhoneNumber(@PathVariable VerifyUsage verifyUsage,
                                              @Parameter(description = "핸드폰번호") @PathVariable String phoneNumber) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(verifyUsage)
                .setVerifyType(VerifyType.PHONE_NUMBER)
                .setVerifyTypeValue(phoneNumber)
        );
    }

    @Operation(summary = "관리자 - 이메일 전송")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send/{verifyUsage:UPDATE_USER|CREATE_USER}/email/{email}")
    public void sendVerifyNumberByEmail(@PathVariable VerifyUsage verifyUsage,
                                        @Parameter(description = "이메일") @PathVariable String email) {
        verifyService.sendVerifyNumberByVerifyTypeValue(VerifyRequest.create()
                .setVerifyUsage(VerifyUsage.SIGNUP)
                .setVerifyType(VerifyType.EMAIL)
                .setVerifyTypeValue(email)
        );
    }

    @Operation(summary = "관리자 - 핸드폰번호 인증확인")
    @PostMapping("/verify/{verifyUsage:UPDATE_USER|CREATE_USER}/phone-number")
    public void verifyByPhoneNumber(@PathVariable VerifyUsage verifyUsage,
                                    @NotBlank(groups = VerifyView.Number.class)
                                    @JsonView(VerifyView.Number.class)
                                    @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(verifyUsage)
                .setVerifyType(VerifyType.PHONE_NUMBER));
    }

    @Operation(summary = "관리자 - 이메일 인증확인")
    @PostMapping("/verify/{verifyUsage:UPDATE_USER|CREATE_USER}/email")
    public void verifyByEmail(@PathVariable VerifyUsage verifyUsage,
                              @NotBlank(groups = VerifyView.Number.class)
                              @JsonView(VerifyView.Number.class)
                              @RequestBody VerifyRequest verifyRequest) {
        verifyService.verifyNumber(verifyRequest
                .setVerifyUsage(verifyUsage)
                .setVerifyType(VerifyType.EMAIL));
    }
}