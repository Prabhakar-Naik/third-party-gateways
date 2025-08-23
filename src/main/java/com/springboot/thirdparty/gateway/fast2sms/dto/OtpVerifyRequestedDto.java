package com.springboot.thirdparty.gateway.fast2sms.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 26-03-2025
 */
@Setter
@Getter
@Data
public class OtpVerifyRequestedDto {
    private String requestId;
    private String code;

    public OtpVerifyRequestedDto(String requestId, String otp) {
        this.requestId = requestId;
        this.code = otp;
    }
}
