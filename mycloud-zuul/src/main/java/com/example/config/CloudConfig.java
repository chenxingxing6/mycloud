package com.example.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * User: lanxinghua
 * Date: 2019/4/12 10:00
 * Desc: cloud配置
 */
@Configuration
public class CloudConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
