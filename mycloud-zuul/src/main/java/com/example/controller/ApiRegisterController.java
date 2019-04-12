package com.example.controller;

import com.example.annotation.Login;
import com.example.annotation.LoginUser;
import com.example.common.utils.R;
import com.example.common.validator.ValidatorUtils;
import com.example.entity.SysUserEntity;
import com.example.form.RegisterForm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 注册接口
 */
@RestController
@RequestMapping("/api")
public class ApiRegisterController {

    @PostMapping("register")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        SysUserEntity user = new SysUserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        return R.ok();
    }

    @Login
    @GetMapping("userInfo")
    public R userInfo(@LoginUser SysUserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    public R userInfo(@RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }
}
