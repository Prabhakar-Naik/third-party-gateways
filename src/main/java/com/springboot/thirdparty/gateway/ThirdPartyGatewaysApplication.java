package com.springboot.thirdparty.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThirdPartyGatewaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdPartyGatewaysApplication.class, args);
		System.out.println("Third Party Integration Application Running Successfully....!");
	}

}
