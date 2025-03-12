package com.springboot.thirdparty.gateway.smscountryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 12-03-2025
 */
@Setter
@Getter
@Data
public class SendSmsResponse {
    @JsonProperty("ApiId")
    private String apiId;
    @JsonProperty("Success")
    private String success;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("MessageUUID")
    private String messageUUID;

    public SendSmsResponse(String apiId, String success, String message,String messageUUID) {
        this.apiId = apiId;
        this.success = success;
        this.message = message;
        this.messageUUID = messageUUID;
    }
}
