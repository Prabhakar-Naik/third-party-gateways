package com.springboot.thirdparty.gateway.whatsapp.dto;

import lombok.Data;

import java.util.List;

/**
 * @author prabhakar, @Date 30-09-2024
 */
@Data
public class WhatsappResponse {
    private String result;
    private String phone_number;
    private List<WhatsappParameters> parameters;
    private boolean validWhatsAppNumber;
    private String otp;
}
