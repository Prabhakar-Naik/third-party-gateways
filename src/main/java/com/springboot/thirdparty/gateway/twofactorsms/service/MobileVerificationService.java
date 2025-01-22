package com.springboot.thirdparty.gateway.twofactorsms.service;

import com.springboot.thirdparty.gateway.twofactorsms.config.TwoFactorConfig;
import com.springboot.thirdparty.gateway.twofactorsms.dto.OTPResponseDto;
import com.springboot.thirdparty.gateway.twofactorsms.dto.OTPVerifyDto;
import com.springboot.thirdparty.gateway.twofactorsms.model.MobileVerification;
import com.springboot.thirdparty.gateway.twofactorsms.repository.MobileVerificationRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class MobileVerificationService {

    private final MobileVerificationRepository mobileVerificationRepository;
    private final RestTemplate restTemplate;
    private final TwoFactorConfig twoFactorConfig;

    public MobileVerificationService(MobileVerificationRepository mobileVerificationRepository, RestTemplate restTemplate, TwoFactorConfig twoFactorConfig) {
        this.mobileVerificationRepository = mobileVerificationRepository;
        this.restTemplate = restTemplate;
        this.twoFactorConfig = twoFactorConfig;
    }


    public ResponseEntity<?> generateOTP(Long mobileNumber) {
        // https://2factor.in/API/V1/:api_key/SMS/:phone_number/AUTOGEN/:otp_template_name

        String API_URL = this.twoFactorConfig.getTwoFactorUrl() + "/" +
                this.twoFactorConfig.getApiKey() +
                "/SMS/" + mobileNumber + "/AUTOGEN/" +
                this.twoFactorConfig.getTemplateName();

        // Call API with path variables
        OTPResponseDto response = this.restTemplate.getForObject(API_URL, OTPResponseDto.class);

        assert response != null;
        if (response.getStatus() != null) {
            MobileVerification state = new MobileVerification();
            state.setMobileNumber(mobileNumber);
            state.setProcessId(response.getDetails());
            state.setVerified(false);
            this.mobileVerificationRepository.save(state);

            return ResponseEntity.ok(new OTPResponseDto(response.getStatus(), response.getDetails()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error occurred while sending OTP: " +
                        response.getStatus());

    }

    public ResponseEntity<?> verifyOtp(OTPVerifyDto otpVerifyDto) {
        // https://2factor.in/API/V1/:api_key/SMS/VERIFY/:otp_session_id/:otp_entered_by_user

        // Replace placeholders with actual values using path variables
        String VERIFY_URL = this.twoFactorConfig.getTwoFactorUrl() + "/" +
                this.twoFactorConfig.getApiKey() +
                "/SMS/VERIFY/" + otpVerifyDto.getOtp_session_id() +
                "/" + otpVerifyDto.getOtp_entered_by_user();


        OTPResponseDto response = this.restTemplate.getForObject(VERIFY_URL, OTPResponseDto.class);

        Optional<MobileVerification> optional = this.mobileVerificationRepository.findByProcessId(otpVerifyDto.getOtp_session_id());
        assert response != null;
        if (response.getStatus().equals("Success")) {
            optional.ifPresent(mobileVerification -> mobileVerification.setVerified(true));

            this.mobileVerificationRepository.save(optional.get());

            return ResponseEntity.ok(new OTPResponseDto(response.getStatus(), response.getDetails()));
        }
        return ResponseEntity.badRequest().body("Error occurred while verifying OTP: " + response.getStatus());
    }


}
