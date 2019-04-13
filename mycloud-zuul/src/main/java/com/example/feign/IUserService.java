package com.example.feign;

import com.example.common.utils.R;
import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc:
 */

@FeignClient(name = "service")
public interface IUserService {
    /**
     * 查询
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/front/app/getUserByAccount")
    SysUserEntity getUserByAccount(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 查询
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/front/app/getUserByMobile")
    SysUserEntity getUserByMobile(@RequestParam("mobile") String mobile);

    /**
     * 查询
     * @param userId
     * @return
     */
    @RequestMapping(value = "/front/app/getUserByUserId")
    SysUserEntity getUserByUserId(@RequestParam("userId") String userId);

    /**
     * 模糊查询
     * @param username
     * @return
     */
    @RequestMapping(value = "/front/app/findUser")
    List<SysUserEntity> findUserByUserName(@RequestParam("username") String username);
}
