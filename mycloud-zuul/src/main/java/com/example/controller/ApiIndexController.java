package com.example.controller;


import com.example.annotation.Login;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页接口
 */
@RestController
@RequestMapping("/api")
public class ApiIndexController {
    @Autowired
    private TokenService tokenService;

    /**
     * 获取手机验证码
     * @return
     */
    @Login
    @RequestMapping("/getIndexData")
    public R getTest(@RequestHeader("token") String token){
        //todo
        Assert.isBlank(token, "token无效");
        return R.ok().put("rankdatas", "")
                .put("ranks", "")
                .put("shareNum", "")
                .put("fileNum","");
    }
}
