package com.pepe.smsgateway.simcom;

import com.fazecast.jSerialComm.SerialPort;

import java.nio.charset.StandardCharsets;

import static com.fazecast.jSerialComm.SerialPort.*;

public class SIMComModule {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SIMComModule.class);
	private static final int READ_TIMEOUT_MILLIS = 3000;    // 3 seconds timeout
	private static final int BUFFER_LENGTH = 10000;

	private final byte[] READ_BUFFER = new byte[BUFFER_LENGTH];
	private final SerialPort serialPort;

	public SIMComModule(String portDescriptor) {

		//SIMCom serial port configuration: 115200 bps, 8 bit data, no parity, 1 bit stop, no data stream control
		serialPort = SerialPort.getCommPort(portDescriptor);
		serialPort.setComPortParameters(115200, 8, ONE_STOP_BIT, NO_PARITY);
		serialPort.setFlowControl(FLOW_CONTROL_DISABLED);
		serialPort.setComPortTimeouts(TIMEOUT_READ_BLOCKING, 3000, 0);

		if (!serialPort.openPort()) {
			String error = "Could not open serial com port with SIMCom module " + portDescriptor;
			log.error(error);
			throw new RuntimeException(error);
		}

		if (!sendCommand(ATCommand.AT).contains("OK")) {
			String error = "Could not send AT command to SIMCom module";
			log.error(error);
			throw new RuntimeException(error);
		}

		log.info("Successfully open serial port and sent AT command to SIMCom module");
	}

	synchronized public String sendCommand(String command) {

		// Make sure command ends with carriage return
		if (!command.endsWith("\r")) {
			command = command.concat("\r");
		}

		writeRequest(command);
		log.debug("Command sent to SIMCom module: " + command);

		String response = readResponse();
		log.debug("Response received from SIMCom module:" + response);

		return response;
	}

	private void writeRequest(String request) {

		serialPort.writeBytes(request.getBytes(StandardCharsets.UTF_8), request.length());
	}

	private String readResponse() {

		String response = "";
		boolean responseFinished = false;
		long timeoutMillis = System.currentTimeMillis() + READ_TIMEOUT_MILLIS;

		while (!responseFinished) {
			int numRead = serialPort.readBytes(READ_BUFFER, serialPort.bytesAvailable());
			response = response.concat(new String(READ_BUFFER, 0, numRead, StandardCharsets.UTF_8));
			responseFinished = isResponseFinished(response);
			if (System.currentTimeMillis() > timeoutMillis) {
				log.warn("Could not find any finish indicator in response");
				return response;
			}
		}

		return response;
	}

	private boolean isResponseFinished(String response) {

		return response.contains("OK") || response.contains("KO") || response.contains("ERROR");
	}

	// Set blocking mode prior to call this function: 	serialPort.setComPortTimeouts(TIMEOUT_READ_BLOCKING, 3000, 0);
	private String readResponseBlocking() {

		int numRead = serialPort.readBytes(READ_BUFFER, BUFFER_LENGTH);
		return new String(READ_BUFFER, 0, numRead, StandardCharsets.UTF_8);
	}
}
