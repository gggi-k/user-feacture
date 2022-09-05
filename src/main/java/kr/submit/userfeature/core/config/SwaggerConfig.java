package kr.submit.userfeature.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.SpringDocConfiguration;
import org.springframework.context.annotation.Import;

@Import(SpringDocConfiguration.class)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwtToken", scheme = "bearer", bearerFormat = "JWT")
@OpenAPIDefinition(
    info = @Info(title = "사용자기능", description = "사용자 로그인,회원가입 등", version = "1.0.0"),
    security = @SecurityRequirement(name = "jwtToken")
)
public class SwaggerConfig {
}