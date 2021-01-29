package com.pepe.smsgateway.simcom;

public class ATCommand {

	public static final String AT = "AT\r";
	public static final String GET_MODULE_INFO = "ATI\r";
	public static final String GET_SIGNAL_STRENGTH = "AT+CSQ\r";
	public static final String GET_BATTERY_LEVEL = "AT+CBC\r";
	public static final String GET_SIM_CARD_NUMBER = "AT+CCID\r";
	public static final String GET_NETWORK_REGISTRATION = "AT+CREG?\r";
	public static final String SET_TEXT_MODE = "AT+CMGF=1\r";

	public static String sendSmsCommand(String number, String text) {

		// AT+CMGS="+34622633644"\nSMSTextContentCntrl+Z(ASCII 26)\r
		return "AT+CMGS=\"+34" + number + "\"\n" + text + (char) 26 + "\r";
	}
}
