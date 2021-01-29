package com.pepe.smsgateway;

import com.pepe.smsgateway.simcom.ATCommand;
import com.pepe.smsgateway.simcom.SIMComModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class SmsGatewayApplicationTests {

	private static final String PORT_DESCRIPTOR = "serial0";

	@Test
	void contextLoads() {
	}

	@Test
	void concurrentSIMComModuleUsageTest() throws InterruptedException, TimeoutException, ExecutionException {

		SIMComModule simComModule = new SIMComModule(PORT_DESCRIPTOR);

		simComModule.sendCommand(ATCommand.GET_MODULE_INFO);
		simComModule.sendCommand(ATCommand.GET_BATTERY_LEVEL);
		simComModule.sendCommand(ATCommand.GET_SIM_CARD_NUMBER);

		CompletableFuture<String> futureResponse1 = CompletableFuture.supplyAsync(() -> simComModule.sendCommand(ATCommand.GET_MODULE_INFO));
		CompletableFuture<String> futureResponse2 = CompletableFuture.supplyAsync(() -> simComModule.sendCommand(ATCommand.GET_BATTERY_LEVEL));
		CompletableFuture<String> futureResponse3 = CompletableFuture.supplyAsync(() -> simComModule.sendCommand(ATCommand.GET_SIM_CARD_NUMBER));

		Thread.sleep(100);
		String response1 = futureResponse1.get(10, TimeUnit.MILLISECONDS);
		String response2 = futureResponse2.get(10, TimeUnit.MILLISECONDS);
		String response3 = futureResponse3.get(10, TimeUnit.MILLISECONDS);

		Assertions.assertTrue(response1.contains("OK"));
		Assertions.assertTrue(response2.contains("OK"));
		Assertions.assertTrue(response3.contains("OK"));
	}
}
