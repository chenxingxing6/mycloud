package com.example.controller;


import com.example.annotation.Login;
import com.example.common.utils.R;
import com.example.common.validator.ValidatorUtils;
import com.example.form.LoginForm;
import com.example.service.TokenService;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    @PostMapping("login")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        //用户登录
        Map<String, Object> map = userService.login(form);
        map.put("token", "6534634");
        return R.ok(map);
    }

    @GetMapping("logout")
    public R logout(@RequestHeader("token") String token){
        tokenService.cleanToken(token);
        return R.ok();
    }

}
