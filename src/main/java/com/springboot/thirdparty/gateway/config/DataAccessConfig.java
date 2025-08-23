package com.springboot.thirdparty.gateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author prabhakar, @Date 27-06-2025
 */

@Getter
@Configuration
public class DataAccessConfig {

    @Value("${access.posts.data.url}")
    private String postsUrl;

    @Value("${access.users.data.url}")
    private String usersUrl;

}
