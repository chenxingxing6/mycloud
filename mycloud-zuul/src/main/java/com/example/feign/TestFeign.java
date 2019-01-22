package com.example.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: lanxinghua
 * Date: 2019/1/22 15:46
 * Desc:
 */
@FeignClient("cloud-shiro")
public interface TestFeign {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String getLoginToken();
}
