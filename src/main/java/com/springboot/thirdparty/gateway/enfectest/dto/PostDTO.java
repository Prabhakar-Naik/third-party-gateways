package com.springboot.thirdparty.gateway.enfectest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prabhakar, @Date 27-06-2025
 */
@Setter
@Getter
@Data
public class PostDTO {
    private int userId;
    private int id;
    private String title;
    private String body;
}
