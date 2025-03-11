package com.springboot.thirdparty.gateway.smscountryapi.controller;

import com.springboot.thirdparty.gateway.smscountryapi.dto.SmsCountryResponse;
import com.springboot.thirdparty.gateway.smscountryapi.service.SmsCountryService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prabhakar, @Date 05-03-2025
 */
@RestController
public class SmsController {

    @Autowired
    private SmsCountryService smsCountryService;

    @GetMapping("/success/sms")
    public String success(){
        return "success";
    }

    @PostMapping("/send-sms")
    public SmsCountryResponse sendSms(@RequestParam String phoneNumber) {
        return smsCountryService.sendSms(phoneNumber);
    }


}
