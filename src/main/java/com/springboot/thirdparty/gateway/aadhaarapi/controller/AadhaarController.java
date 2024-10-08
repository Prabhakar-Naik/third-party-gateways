package com.springboot.thirdparty.gateway.aadhaarapi.controller;

import com.springboot.thirdparty.gateway.aadhaarapi.dto.AadhaarResponseDto;
import com.springboot.thirdparty.gateway.aadhaarapi.dto.OtpRequestDto;
import com.springboot.thirdparty.gateway.aadhaarapi.service.AadhaarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@RestController
@RequestMapping(value = "/api/v1/aadhaar")
public class AadhaarController {

    @Autowired
    private AadhaarService aadhaarService;

    @PostMapping(value = "/generateOtp")
    public ResponseEntity<AadhaarResponseDto> generateOtp(@RequestParam String aadhaarNumber){
        return this.aadhaarService.generateOtpForAadhaar(aadhaarNumber);
    }

    @PostMapping(value = "/verifyOtp")
    public ResponseEntity<AadhaarResponseDto> verifyOtp(@RequestBody OtpRequestDto requestDto){
        return this.aadhaarService.verifyOtp(requestDto);
    }





}
