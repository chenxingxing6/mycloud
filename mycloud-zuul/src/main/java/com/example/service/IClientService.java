package com.example.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: cxx
 * @Date: 2018/12/31 17:42
 */
@FeignClient(name = "service-test")
public interface IClientService {
    @RequestMapping("/")
    String home();
}
