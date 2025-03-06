package com.springboot.thirdparty.gateway.smscountryapi.controller;

import com.springboot.thirdparty.gateway.smscountryapi.service.SmsCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prabhakar, @Date 05-03-2025
 */
@RestController
public class SmsController {

    @Autowired
    private SmsCountryService smsCountryService;

    @GetMapping("/send-sms")
    public String sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        return smsCountryService.sendSms(phoneNumber, message);
    }

    @GetMapping("/success/sms")
    public String success(){
        return "success";
    }
}
