package com.example.feign;

import com.example.entity.FollowUser;
import com.example.entity.ShareEntity;
import com.example.entity.SysUserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * User: lanxinghua
 * Date: 2019/4/12 13:07
 * Desc: 关注
 */

@FeignClient(name = "service")
public interface IFollowService {

    /**
     * 获取关注，未关注用户
     * @param userId
     * @param type   0：关注  1：未关注
     * @return
     */
    @RequestMapping(value = "/front/app/listFollowUser")
    List<FollowUser> listFollowUser(@RequestParam("userId") String userId,
                                    @RequestParam("type") String type);


}
