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
public @interface EmailPattern {

    String message() default "이메일형식에 맞지 않습니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @OverridesAttribute(constraint = Size.class, name = "min")
    int min() default 4;

    @OverridesAttribute(constraint = Size.class, name = "max")
    int max() default 255;

    @OverridesAttribute(constraint = Pattern.class, name = "regexp")
    String regexp() default ".+[@][a-zA-Z]+[.][a-zA-Z]+";
}