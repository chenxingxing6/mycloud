package com.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.annotation.Login;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.feign.ILoginService;
import com.example.feign.OrderService;
import com.example.service.TokenService;
import com.example.service.UserService;
import com.example.utils.MapGet;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ILoginService loginService;

    @RequestMapping("/getTest")
    public String getTest(){
        Map<String, Object> map = new HashMap<>();
        R r1 = loginService.getUserByMobile("18379643982");
        System.out.println(r1);

        R r2 = loginService.getUserByAccount("front1", "123456");
        System.out.println(r2);
        return orderService.getTest();
    }

    @PostMapping("login")
    public R login(@RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("type", params);
        Assert.isBlank(type, "参数错误");
        //账号登录
        if ("0".equals(type)){
            String username = MapGet.getByKey("account", params);
            String password = MapGet.getByKey("password", params);
        }
        //手机登录
        else if ("1".equals(type)){
            String username = MapGet.getByKey("captcha", params);
            String password = MapGet.getByKey("mobile", params);
        }

        //生成token
        long userId = 1;
        String token = tokenService.createToken(userId);

        //用户登录
        Map<String, Object> map = new HashMap<>();
        UserVo userVo = new UserVo();
        userVo.setId(1);
        userVo.setUsername("蓝星花");
        userVo.setEmail("lanxinghua@2dfire.com");
        userVo.setImgPath("https://static.vilson.xyz/cover.png");
        userVo.setPassword("3512352135");
        userVo.setMobile("18379643981");
        userVo.setCreateTime("2019-04-09 14:39:58");
        userVo.setDeptId(1);
        userVo.setDeptName("智慧商圈");
        userVo.setCompanyName("杭州二维火科技有限公司");
        map.put("member", userVo);
        return R.ok().put("data", map).put("token", token);
    }

    @Login
    @GetMapping("logout")
    public R logout(@RequestHeader("token") String token){
        tokenService.cleanToken(token);
        return R.ok();
    }

}
