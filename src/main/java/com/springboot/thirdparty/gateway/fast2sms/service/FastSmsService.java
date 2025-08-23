package com.springboot.thirdparty.gateway.fast2sms.service;

import com.springboot.thirdparty.gateway.fast2sms.config.FastSmsConfig;
import com.springboot.thirdparty.gateway.fast2sms.dto.FastSmsResponseDto;
import com.springboot.thirdparty.gateway.fast2sms.dto.OtpVerifyRequestedDto;
import com.springboot.thirdparty.gateway.fast2sms.model.FastSms;
import com.springboot.thirdparty.gateway.fast2sms.repository.FastSmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * @author prabhakar, @Date 26-03-2025
 */

@Service
public class FastSmsService {

    @Autowired
    private FastSmsRepository fastSmsRepository;
    @Autowired
    private FastSmsConfig fastSmsConfig;

    public FastSmsResponseDto sendOtp(Long phoneNumber) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", fastSmsConfig.getApiKey());
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        int otp = generateOTP();
        String requestBody = "variables_values=" + otp + "&route=otp&numbers=" + phoneNumber;

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Use UriComponentsBuilder to construct the URL with parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fastSmsConfig.getFast2smsUrl());

        ResponseEntity<FastSmsResponseDto> responseEntity = restTemplate.exchange(
                builder.toUriString(), // The complete URL
                HttpMethod.POST,
                requestEntity,
                FastSmsResponseDto.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            FastSms fastSms = new FastSms();
            fastSms.setRequestId(Objects.requireNonNull(responseEntity.getBody()).getRequestId());
            fastSms.setMobileNumber(phoneNumber);
            fastSms.setOtp(otp);
            fastSms.setVerified(false);
            this.fastSmsRepository.save(fastSms);

            return responseEntity.getBody();
        } else {
            System.err.println("Fast2SMS API error: " + responseEntity.getStatusCode() + " - " + responseEntity.getBody());
            return responseEntity.getBody();
        }
    }


    public String verifyOtp(OtpVerifyRequestedDto otpVerifyRequestedDto) {
        Optional<FastSms> entity = this.fastSmsRepository.findByRequestId(otpVerifyRequestedDto.getRequestId());
        if (entity.isPresent()){
            if (entity.get().getOtp().equals(Integer.parseInt(otpVerifyRequestedDto.getCode()))){
                entity.get().setVerified(true);
                this.fastSmsRepository.save(entity.get());
                return "Verification Success.";
            }
            return "Verification Failed.";
        }
        return "Verification Failed.";
    }

    private static int generateOTP() {
        // Using Random class
        Random random = new Random();
        // Generates number between 100000 and 999999
        return 100000 + random.nextInt(900000);
    }
}
