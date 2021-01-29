package com.pepe.smsgateway.webservice.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpanishMobileNumberValidator.class)
@Documented
public @interface SpanishMobileNumber {

	String message() default "Invalid spanish mobile number format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
