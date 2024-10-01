package com.springboot.thirdparty.gateway.whatsapp.dto;

import lombok.Data;

import java.util.List;

/**
 * @author prabhakar, @Date 30-09-2024
 */
@Data
public class WhatsappRequest {
    private String template_name;
    private String broadcast_name;
    private List<WhatsappParameters> parameters;
}
