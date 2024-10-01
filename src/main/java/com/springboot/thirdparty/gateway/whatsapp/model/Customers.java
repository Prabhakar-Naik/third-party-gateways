package com.springboot.thirdparty.gateway.whatsapp.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author prabhakar, @Date 30-09-2024
 */
@Entity
@Table(name="customers")
@Data
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String otp;
    private String email;
    private String otpStatus;
}
