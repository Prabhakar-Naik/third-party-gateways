package com.springboot.thirdparty.gateway.stripe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author prabhakar, @Date 24-11-2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
