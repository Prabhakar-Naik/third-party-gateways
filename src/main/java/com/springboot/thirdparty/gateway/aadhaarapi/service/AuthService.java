package com.springboot.thirdparty.gateway.aadhaarapi.service;

import com.springboot.thirdparty.gateway.aadhaarapi.config.SandBoxConfig;
import lombok.Getter;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @author prabhakar, @Date 03-10-2024
 */
@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final SandBoxConfig sandBoxConfig;

    private String accessToken;
    private long tokenExpiryTime;

    @Autowired
    public AuthService(RestTemplateBuilder restTemplateBuilder, SandBoxConfig sandBoxConfig) {
        this.restTemplate = restTemplateBuilder.build();
        this.sandBoxConfig = sandBoxConfig;
    }

    public String fetchAccessToken() {
        String url = this.sandBoxConfig.getSandboxUrl()+"/authenticate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");
        headers.set("x-api-key",this.sandBoxConfig.getApiKey());
        headers.set("x-api-secret",this.sandBoxConfig.getSecretKey());
        headers.set("x-api-version",this.sandBoxConfig.getVersion());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
        this.accessToken = Objects.requireNonNull(response.getBody()).get("access_token");
        this.tokenExpiryTime = System.currentTimeMillis() + 86400000; // 24 hours in milliseconds
        return this.accessToken;
    }


    public String refreshAccessToken() {
        String url = this.sandBoxConfig.getSandboxUrl()+"/authorize?request_token="+this.fetchAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization",this.accessToken);
        headers.set("x-api-key", this.sandBoxConfig.getApiKey());
        headers.set("x-api-version",this.sandBoxConfig.getVersion());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
        this.accessToken = Objects.requireNonNull(response.getBody()).get("access_token");
        this.tokenExpiryTime = System.currentTimeMillis() + 86400000; // 24 hours in milliseconds
        return this.accessToken;
    }


    public String getAccessToken() {
        if (System.currentTimeMillis() > tokenExpiryTime) {
            return fetchAccessToken();
        }
        return this.accessToken;
    }




}
