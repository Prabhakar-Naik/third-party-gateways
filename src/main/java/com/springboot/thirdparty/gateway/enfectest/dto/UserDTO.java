package com.springboot.thirdparty.gateway.enfectest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 27-06-2025
 */
@Setter
@Getter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private int id;
    private Address address;
    private Company company;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private Geo geo;
    }
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geo {
        private String lat;
        private String lng;
    }
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Company {
        private String name;
    }
}
