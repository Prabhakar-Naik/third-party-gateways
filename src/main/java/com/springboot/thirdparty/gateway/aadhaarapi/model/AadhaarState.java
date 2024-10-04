package com.springboot.thirdparty.gateway.aadhaarapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 01-10-2024
 */
@Setter
@Getter
@Entity
public class AadhaarState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("reference_id")
    private String referenceId;
    @JsonProperty("aadhaar_number")
    private String aadhaarNumber;
    private String status;

}
