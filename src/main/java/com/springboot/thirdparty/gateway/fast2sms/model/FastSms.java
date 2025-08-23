package com.springboot.thirdparty.gateway.fast2sms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 26-03-2025
 */
@Setter
@Getter
@Entity
public class FastSms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String requestId;
    private Long mobileNumber;
    private Integer otp;
    private Boolean verified;
}
