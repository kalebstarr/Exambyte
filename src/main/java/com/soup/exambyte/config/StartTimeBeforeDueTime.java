package com.soup.exambyte.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = StartTimeBeforeDueTimeValidator.class)
public @interface StartTimeBeforeDueTime {
  String message() default "Start time must be before due time";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
