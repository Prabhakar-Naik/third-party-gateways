package com.springboot.thirdparty.gateway.twofactorsms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OTPResponseDto {
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Details")
    private String details;

    public OTPResponseDto(String status, String details) {
        this.status = status;
        this.details = details;
    }
}
