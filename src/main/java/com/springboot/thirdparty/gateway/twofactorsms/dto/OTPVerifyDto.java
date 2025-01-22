package com.springboot.thirdparty.gateway.twofactorsms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OTPVerifyDto {
    @JsonProperty("otp_session_id")
    private String otp_session_id;
    @JsonProperty("otp_entered_by_user")
    private String otp_entered_by_user;
}
