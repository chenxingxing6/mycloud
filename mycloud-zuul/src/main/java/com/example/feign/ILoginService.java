package com.example.feign;

import com.example.common.utils.R;
import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc:
 */

@FeignClient(name = "service")
public interface ILoginService {
    @RequestMapping(value = "/front/getUserByAccount")
    SysUserEntity getUserByAccount(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "/front/getUserByMobile")
    SysUserEntity getUserByMobile(@RequestParam("mobile") String mobile);
}
