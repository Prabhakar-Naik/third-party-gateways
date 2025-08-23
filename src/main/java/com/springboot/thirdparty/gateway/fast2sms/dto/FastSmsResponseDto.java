package com.springboot.thirdparty.gateway.fast2sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author prabhakar, @Date 26-03-2025
 */
@Setter
@Getter
@Data
public class FastSmsResponseDto {
    @JsonProperty("return")
    private boolean success;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("message")
    private List<String> message;

    public FastSmsResponseDto(boolean success, String requestId, List<String> message) {
        this.success = success;
        this.requestId = requestId;
        this.message = message;
    }

}
