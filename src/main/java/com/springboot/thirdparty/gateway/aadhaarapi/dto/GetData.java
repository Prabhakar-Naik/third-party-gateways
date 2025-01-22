package com.springboot.thirdparty.gateway.aadhaarapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Data
public class GetData {
    @JsonProperty("@entity")
    private String entity;
    @JsonProperty("reference_id")
    private String referenceId;
    private String message;
}
