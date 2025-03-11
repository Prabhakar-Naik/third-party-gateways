package com.springboot.thirdparty.gateway.smscountryapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 11-03-2025
 */
@Setter
@Getter
@Entity
public class SmsCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String messageID;
    private Long mobile;
    private Boolean isOtpSend;
}
