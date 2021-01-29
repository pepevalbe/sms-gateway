package com.pepe.smsgateway.webservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private String result;
	private List<String> errors;

	public Response(String result) {
		this.result = result;
	}
}
