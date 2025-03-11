package com.springboot.thirdparty.gateway.smscountryapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author prabhakar, @Date 11-03-2025
 */
@Getter
@Configuration
public class SmsCountryConfig {

    @Value("${sms.country.auth.key}")
    private String authKey;

    @Value("${sms.country.auth.token}")
    private String authToken;

    @Value("${sms.country.api.url}")
    private String apiUrl;

    @Value("${sms.country.message}")
    private String message;

    @Value("${sms.country.sender.id}")
    private String senderId;

    @Value("${sms.country.dr.notify.url}")
    private String drNotifyUrl;

}
