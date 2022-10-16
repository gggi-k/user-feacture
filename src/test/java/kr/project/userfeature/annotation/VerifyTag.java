package kr.project.userfeature.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("Verify")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyTag {
}