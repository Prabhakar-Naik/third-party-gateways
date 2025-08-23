package com.springboot.thirdparty.gateway.aadhaarapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Getter
@Configuration
public class SandBoxConfig {

    @Value("${sandbox.api.url}")
    private String sandboxUrl;

    @Value("${sandbox.api.key}")
    private String apiKey;

    @Value("${sandbox.api.secret.key}")
    private String secretKey;

    @Value("${sandbox.api.version}")
    private String version;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

}
