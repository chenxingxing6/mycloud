package com.example.controller;


import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/api")
public class ApiTestController {

    @Login
    @GetMapping("userInfo")
    public R userInfo(@ApiIgnore @LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }

}
