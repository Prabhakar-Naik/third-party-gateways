package com.springboot.thirdparty.gateway.twofactorsms.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class TwoFactorConfig {

    @Value("${2factor.api.url}")
    private String twoFactorUrl;

    @Value("${2factor.api.key}")
    private String apiKey;

    @Value("${2factor.template.name}")
    private String templateName;


}
