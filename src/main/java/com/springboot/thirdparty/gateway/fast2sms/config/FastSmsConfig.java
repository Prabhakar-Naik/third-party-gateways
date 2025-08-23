package com.springboot.thirdparty.gateway.fast2sms.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author prabhakar, @Date 26-03-2025
 */
@Getter
@Configuration
public class FastSmsConfig {

    @Value("${fast2sms.api.url}")
    private String fast2smsUrl;

    @Value("${fast2sms.api.key}")
    private String apiKey;

    @Value("${fast2sms.api.otp.route}")
    private String otpRoute;

    @Value("${fast2sms.api.otp.message}")
    private String otpMessage;

    @Value("${fast2sms.api.q.route}")
    private String quickRoute;

    @Value("${fast2sms.api.q.message}")
    private String quickMessage;

    @Value("${fast2sms.api.language}")
    private String language;


}
