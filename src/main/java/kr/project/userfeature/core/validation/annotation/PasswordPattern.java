package kr.project.userfeature.core.validation.annotation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Size
@Pattern(regexp = "")
@ReportAsSingleViolation
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordPattern {

    String message() default "비밀번호형식에 맞지 않습니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @OverridesAttribute(constraint = Size.class, name = "min")
    int min() default 8;

    @OverridesAttribute(constraint = Size.class, name = "max")
    int max() default Integer.MAX_VALUE;

    @OverridesAttribute(constraint = Pattern.class, name = "regexp")
    String regexp() default "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
}