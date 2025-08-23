package com.springboot.thirdparty.gateway.fast2sms.controller;

import com.springboot.thirdparty.gateway.aadhaarapi.dto.OtpRequestDto;
import com.springboot.thirdparty.gateway.fast2sms.dto.FastSmsResponseDto;
import com.springboot.thirdparty.gateway.fast2sms.dto.OtpVerifyRequestedDto;
import com.springboot.thirdparty.gateway.fast2sms.service.FastSmsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author prabhakar, @Date 26-03-2025
 */

@RestController
@RequestMapping(value = "/fastSms")
public class FastSmsController {

    private final FastSmsService fastSmsService;

    public FastSmsController(FastSmsService fastSmsService) {
        this.fastSmsService = fastSmsService;
    }

    @PostMapping(value = "/sendOtp/{phoneNumber}")
    public FastSmsResponseDto sendOtp(@PathVariable Long phoneNumber) {
        return this.fastSmsService.sendOtp(phoneNumber);
    }

    @PostMapping(value = "/verifyOtp")
    public String verifyOtp(@RequestParam String requestId,@RequestParam String otp) {
        return this.fastSmsService.verifyOtp(new OtpVerifyRequestedDto(requestId,otp));
    }


}
