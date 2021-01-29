package com.pepe.smsgateway.webservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpanishMobileNumberValidator implements ConstraintValidator<SpanishMobileNumber, String> {

	private static final String PATTERN = "[67]([0-9]){8}";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return value != null && value.matches(PATTERN);
	}
}
