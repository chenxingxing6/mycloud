package com.example.feign;

import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc:
 */

@FeignClient(name = "service", url = "http://localhost:9000")
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
     * @param openId
     * @return
     */
    @RequestMapping(value = "/front/app/getUserByOpenId")
    SysUserEntity getUserByOpenId(@RequestParam("openId") String openId);

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


    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/front/app/updateUser", method = RequestMethod.POST)
    void updateUser(@RequestBody SysUserEntity user);
}
