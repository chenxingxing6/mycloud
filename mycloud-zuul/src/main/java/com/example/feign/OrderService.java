package com.example.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * User: lanxinghua
 * Date: 2019/4/12 09:51
 * Desc: RestTemplate方式请求http
 */
@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    public String getTest(){
        return restTemplate.getForObject("http://localhost:9000/front/login", String.class);
    }
}
