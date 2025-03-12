package com.springboot.thirdparty.gateway.smscountryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 11-03-2025
 */
@Setter
@Getter
@Data
public class SmsCountryResponse {
    private SendSmsResponse sendSmsResponse;
    private SendBulkSmsResponse sendBulkSmsResponse;

    public SmsCountryResponse(SendSmsResponse sendSmsResponse) {
        this.sendSmsResponse = sendSmsResponse;
    }

    public SmsCountryResponse(SendBulkSmsResponse sendBulkSmsResponse) {
        this.sendBulkSmsResponse = sendBulkSmsResponse;
    }

}
