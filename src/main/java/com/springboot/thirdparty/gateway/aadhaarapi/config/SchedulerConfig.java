package com.springboot.thirdparty.gateway.aadhaarapi.config;

import com.springboot.thirdparty.gateway.aadhaarapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author prabhakar, @Date 03-10-2024
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final AuthService authService;

    @Autowired
    public SchedulerConfig(AuthService authService) {
        this.authService = authService;
    }

//    @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
//    public void refreshAccessToken() {
//        authService.fetchAccessToken();
//    }

    @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
    public void refreshAccessToken() {
        authService.refreshAccessToken();
    }

}
