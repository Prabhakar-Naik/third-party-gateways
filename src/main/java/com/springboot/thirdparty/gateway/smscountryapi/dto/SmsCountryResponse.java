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
    @JsonProperty("ApiId")
    private String apiId;
    @JsonProperty("MessageUUID")
    private String messageUUID;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Success")
    private String success;

    public SmsCountryResponse(String apiId, String messageUUID, String message, String success) {
        this.apiId = apiId;
        this.messageUUID = messageUUID;
        this.message = message;
        this.success = success;
    }

}
