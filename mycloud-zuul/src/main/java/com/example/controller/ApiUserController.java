package com.example.controller;


import com.example.annotation.Login;
import com.example.common.utils.R;
import com.example.common.validator.ValidatorUtils;
import com.example.form.LoginForm;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api/user")
public class ApiUserController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/info")
    public R login(){
        return R.ok();
    }

}
