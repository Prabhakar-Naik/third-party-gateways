package com.springboot.thirdparty.gateway.twofactorsms.controller;

import com.springboot.thirdparty.gateway.twofactorsms.dto.OTPVerifyDto;
import com.springboot.thirdparty.gateway.twofactorsms.service.MobileVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mobileVerification")
public class MobileVerificationController {


    private final MobileVerificationService mobileVerificationService;

    public MobileVerificationController(MobileVerificationService mobileVerificationService) {
        this.mobileVerificationService = mobileVerificationService;
    }

    @PostMapping(value = "/generateOtp/AUTOGEN")
    public ResponseEntity<?> generateOTP(@RequestParam Long mobileNumber){
        return this.mobileVerificationService.generateOTP(mobileNumber);
    }

    @PostMapping(value = "/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OTPVerifyDto otpVerifyDto){
        return this.mobileVerificationService.verifyOtp(otpVerifyDto);
    }

}
