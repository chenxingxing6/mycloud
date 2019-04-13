package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.FollowUser;
import com.example.entity.SysUserEntity;
import com.example.feign.IFollowService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.FileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关注用户
 */
@RestController
@RequestMapping("/api")
public class ApiFollowController {
    @Autowired
    private IFollowService followService;


    /**
     * 关注用户 已关注的用户
     * @param user
     * @param params type:0 未关注  1:已关注
     * @return
     */
    @Login
    @RequestMapping("/getFollowUser")
    public R getFollowUser(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("type", params);
        Assert.isBlank(type, "参数错误");
        Assert.isNull(user, "用户信息缺失");
        String userId = String.valueOf(user.getUserId());
        List<FollowUser> followUsers = followService.listFollowUser(userId, type);
        Map<String, Object> map = new HashMap<>();
        map.put("list", followUsers);
        return R.ok().put("data", map);
    }

}