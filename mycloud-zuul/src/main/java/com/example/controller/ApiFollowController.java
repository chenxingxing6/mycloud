package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.FollowUser;
import com.example.entity.SysUserEntity;
import com.example.feign.IFollowService;
import com.example.feign.IUserService;
import com.example.service.TokenService;
import com.example.utils.MapGet;
import com.example.vo.FileVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 关注用户
 */
@RestController
@RequestMapping("/api")
public class ApiFollowController {
    @Autowired
    private IFollowService followService;
    @Autowired
    private IUserService userService;


    /**
     * 关注用户 已关注的用户
     * @param user
     * @param params type:0 未关注  1:已关注
     * @return
     */
    @Login
    @RequestMapping("/getFollowUser")
    public R getFollowUser(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("searchType", params);
        String userName = MapGet.getByKey("keyword", params);
        Assert.isBlank(type, "参数错误");
        Assert.isNull(user, "用户信息缺失");
        String userId = String.valueOf(user.getUserId());
        List<SysUserEntity> followUsers = followService.listFollowUser(userId, type);
        //过滤用户名
        if (StringUtils.isNotEmpty(userName)){
            followUsers = followUsers.stream().filter(new Predicate<SysUserEntity>() {
                @Override
                public boolean test(SysUserEntity sysUserEntity) {
                    return sysUserEntity.getUsername().contains(userName);
                }
            }).collect(Collectors.toList());
        }
        for (SysUserEntity followUser : followUsers) {
            followUser.setType(Integer.valueOf(type));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", followUsers);
        map.put("total", followUsers.size());
        return R.ok().put("data", map);
    }


    /**
     * 获取用户详情
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/getUserDetail")
    public R getUserDetail(@RequestParam Map<String, Object> params){
        String userId = MapGet.getByKey("userId", params);
        Assert.isBlank(userId, "参数错误");
        SysUserEntity user = userService.getUserByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("member", user);
        return R.ok().put("data", map);
    }


    /**
     * 关注或取消关注
     * @param params
     * @return
     */
    @Login
    @RequestMapping("/addOrCancleUser")
    public R addOrCancleUser(@LoginUser SysUserEntity user, @RequestParam Map<String, Object> params){
        Assert.isNull(user, "参数错误");
        String type = MapGet.getByKey("type", params);
        String toUserId = MapGet.getByKey("userId", params);
        String fromUserId = user.getUserId().toString();
        Assert.isBlank(toUserId, "参数错误");
        Assert.isBlank(type, "参数错误");
        followService.addOrCancleUser(fromUserId, toUserId, type);
        return R.ok();
    }
}
