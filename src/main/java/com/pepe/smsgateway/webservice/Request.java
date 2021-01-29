package com.pepe.smsgateway.webservice;

import com.pepe.smsgateway.webservice.validator.SpanishMobileNumber;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Request {

	@SpanishMobileNumber
	private String number;

	@NotNull
	@Size(min = 1, max = 160)
	private String text;
}
