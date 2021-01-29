package com.pepe.smsgateway.webservice;

import com.pepe.smsgateway.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TextController {

	@Autowired
	TextService textService;

	@PostMapping("/sendtext")
	ResponseEntity<Response> sendSmsText(@RequestBody @Valid Request request) {

		boolean result = textService.sendText(request.getNumber(), request.getText());

		if (result) {
			return ResponseEntity.ok(new Response("Sms successfully sent"));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Could not send sms"));
		}
	}
}
