package com.springboot.thirdparty.gateway.smscountryapi.service;

import com.paypal.base.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prabhakar, @Date 05-03-2025
 */

@Service
public class SmsCountryService {

    private static final Logger logger = LoggerFactory.getLogger(SmsCountryService.class);

    @Value("${sms.country.auth.key}")
    private String authKey;

    @Value("${sms.country.auth.token}")
    private String authToken;

    @Value("${sms.country.api.url}")
    private String apiUrl;

    @Value("${sms.country.sender.id}")
    private String senderId;

    @Value("${sms.country.dr.notify.url}")
    private String drNotifyUrl;

    public String sendSms(String phoneNumber, String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Basic Authentication
            String auth = authKey + ":" + authToken;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("Text", message); // Use "Text" as specified
            requestBody.put("Number", phoneNumber); // Use "Number" as specified
            requestBody.put("SenderId", senderId);
            requestBody.put("DRNotifyUrl", drNotifyUrl);
            requestBody.put("DRNotifyHttpMethod", "POST"); // Default value
            requestBody.put("Tool", "API"); // Default value

            logger.info("SMSCountry Request Payload: {}", requestBody);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(apiUrl, entity, String.class);
            logger.info("SMSCountry API Response: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error sending SMS: {}", e.getMessage(), e);
            return "Error sending SMS: " + e.getMessage();
        }

    }
}