package com.springboot.thirdparty.gateway.smscountryapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prabhakar, @Date 12-03-2025
 */
@Setter
@Getter
@Data
public class SendBulkSmsResponse {

    @JsonProperty("ApiId")
    private String apiId;
    @JsonProperty("Success")
    private String success;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("BatchId")
    private String batchId;
    @JsonProperty("MessageUUIDs")
    private String messageUUIDs;

    public SendBulkSmsResponse(String apiId, String success, String message, String batchId, String messageUUIDs) {
        this.apiId = apiId;
        this.success = success;
        this.message = message;
        this.batchId = batchId;
        this.messageUUIDs = messageUUIDs;

    }
     /*
    "ApiId": "4236749c-0d5c-4b1e-9598-3260e688d616",
  "Success": true,
  "Message": "Messages Queued",
  "BatchId": "4236749c-0d5c-4b1e-9598-3260e688d616",
  "MessageUUIDs": [
    "4236749c-0d5c-4b1e-9598-3260e688d616",
    "4236749c-0d5c-4b1e-9598-3260e688d616"
  ]
    */
}
