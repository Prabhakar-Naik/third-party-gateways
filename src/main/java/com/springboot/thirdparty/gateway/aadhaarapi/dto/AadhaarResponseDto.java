package com.springboot.thirdparty.gateway.aadhaarapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Setter
@Getter
@Data
public class AadhaarResponseDto {
    private Long timestamp;
    @JsonProperty("transaction_id")
    private String transactionId;
    private GetData data;
    private Integer code;
}
