package com.springboot.thirdparty.gateway.aadhaarapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Data
public class AadhaarRequestDto {
    @JsonProperty("@entity")
    private String entity;
    @JsonProperty("aadhaar_number")
    private String aadhaarNumber;
    private String consent;
    private String reason;
}
