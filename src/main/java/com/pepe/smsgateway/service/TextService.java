package com.pepe.smsgateway.service;

import com.pepe.smsgateway.simcom.ATCommand;
import com.pepe.smsgateway.simcom.SIMComModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextService {

	private static final String PORT_DESCRIPTOR = "serial0";

	private final SIMComModule simComModule;

	public TextService() {

		// Open SIMCom port and check it is working
		simComModule = new SIMComModule(PORT_DESCRIPTOR);

		// Set text mode
		if (simComModule.sendCommand(ATCommand.SET_TEXT_MODE).contains("OK")) {
			log.info("Successfully set SIMCom module in text mode");
		} else {
			String error = "Could not set SIMCom module in text mode";
			log.error(error);
			throw new RuntimeException(error);
		}
	}

	public boolean sendText(String number, String text) {

		String response = simComModule.sendCommand(ATCommand.sendSmsCommand(number, text));

		if (response.contains("OK")) {
			log.info("Successfully sent text to " + number);
			return true;
		} else {
			log.info("Could not send text to " + number);
			return false;
		}
	}
}
