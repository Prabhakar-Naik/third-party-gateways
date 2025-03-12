package com.springboot.thirdparty.gateway.smscountryapi.service;

import com.paypal.base.codec.binary.Base64;
import com.springboot.thirdparty.gateway.smscountryapi.config.SmsCountryConfig;
import com.springboot.thirdparty.gateway.smscountryapi.dto.SendBulkSmsResponse;
import com.springboot.thirdparty.gateway.smscountryapi.dto.SendSmsResponse;
import com.springboot.thirdparty.gateway.smscountryapi.dto.SmsCountryResponse;
import com.springboot.thirdparty.gateway.smscountryapi.model.SmsCountry;
import com.springboot.thirdparty.gateway.smscountryapi.repository.SmsCountryRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author prabhakar, @Date 05-03-2025
 */

@Service
public class SmsCountryService {

    private static final Logger logger = LoggerFactory.getLogger(SmsCountryService.class);

    private final RestTemplate restTemplate;
    private final SmsCountryRepository smsCountryRepository;
    private final SmsCountryConfig smsCountryConfig;

    public SmsCountryService(RestTemplate restTemplate, SmsCountryRepository smsCountryRepository, SmsCountryConfig smsCountryConfig) {
        this.restTemplate = restTemplate;
        this.smsCountryRepository = smsCountryRepository;
        this.smsCountryConfig = smsCountryConfig;
    }

    public SendSmsResponse sendSms(String phoneNumber) {
        try {
            HttpHeaders headers = getAuthHttpHeaders();

            Map<String, String> requestBody = getRequestBody(phoneNumber);

            logger.info("Request Payload : {}", requestBody);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            String API_URL = smsCountryConfig.getApiUrl().replace("authKey", smsCountryConfig.getAuthKey());
            API_URL = API_URL.concat("/SMSes/");

            SendSmsResponse response = restTemplate.exchange(API_URL, HttpMethod.POST, entity,
                    SendSmsResponse.class).getBody();

            assert response != null;
            if (response.getSuccess().equals("True")) {
                SmsCountry state = new SmsCountry();
                state.setMobile(Long.valueOf(phoneNumber));
                state.setMessageID(response.getMessageUUID());
                state.setIsOtpSend(true);
                smsCountryRepository.save(state);
            }

            return response;
        } catch (Exception e) {
            logger.error("Error sending SMS: {}", e.getMessage(), e);
            return new SendSmsResponse("null", "Failed", "Error", "failed");
        }

    }


    public SendBulkSmsResponse sendBulkSms(List<String> phoneNumbers) {
        try {
            HttpHeaders headers = getAuthHttpHeaders();

            Map<String, Object> requestBody = getRequestBodyForBulk(phoneNumbers);

            logger.info("Request Payload: {}", requestBody);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String API_URL = smsCountryConfig.getApiUrl().replace("authKey", smsCountryConfig.getAuthKey());
            API_URL = API_URL.concat("/BulkSMSes/");

            SendBulkSmsResponse response = restTemplate.exchange(API_URL, HttpMethod.POST, entity,
                    SendBulkSmsResponse.class).getBody();

            assert response != null;
            String data = response.getMessageUUIDs();
            data = data.replace("[", "");
            data = data.replace("]", "");
            data = data.replace(",", "");
            List<String> dataList = Arrays.asList(data.split(" "));
            if (response.getSuccess().equals("False")) {
                return new SendBulkSmsResponse(response.getApiId(), response.getSuccess(), response.getMessage(), "null", response.getMessageUUIDs());
            }
            for (int i=0; i<dataList.size(); i++) {
                if (response.getSuccess().equals("True")) {
                    SmsCountry state = new SmsCountry();
                    state.setMobile(Long.valueOf(phoneNumbers.get(i)));
                    state.setMessageID(dataList.get(i));
                    state.setIsOtpSend(true);
                    smsCountryRepository.save(state);
                }
            }

            return response;
        } catch (Exception e) {
            logger.error("Error sending SMS: {}", e.getMessage(), e);
            return new SendBulkSmsResponse("null", "Failed", "Error","null", "Error");
        }
    }


    @NotNull
    private HttpHeaders getAuthHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // Basic Authentication
        String auth = smsCountryConfig.getAuthKey() + ":" + smsCountryConfig.getAuthToken();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        return headers;
    }

    @NotNull
    private Map<String, String> getRequestBody(String phoneNumber) {
        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("Text", smsCountryConfig.getMessage()); // Use "Text" as specified
        requestBody.put("Number", phoneNumber); // Use "Number" as specified
        requestBody.put("SenderId", smsCountryConfig.getSenderId());
        requestBody.put("DRNotifyUrl", smsCountryConfig.getDrNotifyUrl());
        requestBody.put("DRNotifyHttpMethod", "POST"); // Default value
        requestBody.put("Tool", "API"); // Default value
        return requestBody;
    }

    @NotNull
    private Map<String, Object> getRequestBodyForBulk(List<String> phoneNumbers) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("Text", smsCountryConfig.getMessage());
        requestBody.put("Numbers",phoneNumbers);
        requestBody.put("SenderId", smsCountryConfig.getSenderId());
        requestBody.put("DRNotifyUrl", smsCountryConfig.getDrNotifyUrl());
        requestBody.put("DRNotifyHttpMethod", "POST"); // Default value
        requestBody.put("Tool", "API"); // Default value
        return requestBody;
    }

}