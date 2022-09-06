package kr.submit.userfeature.core.swagger.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponses({
    @ApiResponse(responseCode = "200",description = "성공"),
    @ApiResponse(responseCode = "201",description = "생성됨"),
    @ApiResponse(responseCode = "202",description = "허용됨", content = @Content),
    @ApiResponse(responseCode = "204",description = "응답할 값이 없습니다", content = @Content),
    @ApiResponse(responseCode = "400",description = "잘못된 값입니다", content = @Content),
    @ApiResponse(responseCode = "401",description = "인증이 필요합니다", content = @Content),
    @ApiResponse(responseCode = "403",description = "접근이 금지되었습니다", content = @Content),
    @ApiResponse(responseCode = "404",description = "존재하지 않습니다", content = @Content),
    @ApiResponse(responseCode = "409",description = "중복됩니다", content = @Content),
    @ApiResponse(responseCode = "500",description = "에러가 발생하였습니다", content = @Content)
})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SwaggerApiResponses {
}