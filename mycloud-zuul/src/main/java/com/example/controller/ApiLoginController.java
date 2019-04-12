package com.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.annotation.Login;
import com.example.common.exception.BizException;
import com.example.common.utils.DateUtils;
import com.example.common.utils.R;
import com.example.common.validator.Assert;
import com.example.entity.SysUserEntity;
import com.example.feign.ILoginService;
import com.example.feign.OrderService;
import com.example.service.TokenService;
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
    private TokenService tokenService;
    @Autowired
    private ILoginService loginService;


    /**
     * 获取手机验证码
     * @param params
     * @return
     */
    @RequestMapping("/getCaptcha")
    public R getTest(@RequestParam Map<String, Object> params){
        String mobile = MapGet.getByKey("mobile", params);
        Assert.isBlank(mobile, "参数错误");
        return R.ok().put("data", tokenService.createCaptcha(mobile));
    }

    /**
     * 登陆
     *
     * @param params
     * @return
     */
    @PostMapping("login")
    public R login(@RequestParam Map<String, Object> params){
        String type = MapGet.getByKey("type", params);
        Assert.isBlank(type, "参数错误");
        SysUserEntity userEntity = null;
        //账号登录
        if ("0".equals(type)){
            String username = MapGet.getByKey("account", params);
            String password = MapGet.getByKey("password", params);
            userEntity = loginService.getUserByAccount(username, password);
        }
        //手机登录
        else if ("1".equals(type)){
            String username = MapGet.getByKey("captcha", params);
            String password = MapGet.getByKey("mobile", params);
            userEntity = loginService.getUserByMobile(password);
        }
        if (userEntity == null){
            throw new BizException("用户账号或密码错误");
        }

        //生成token
        String token = tokenService.createToken(userEntity.getUserId());

        //用户登录
        Map<String, Object> map = new HashMap<>();
        UserVo userVo = new UserVo();
        userVo.setId(userEntity.getUserId().toString());
        userVo.setUsername(userEntity.getUsername());
        userVo.setEmail(userEntity.getEmail());
        userVo.setImgPath(userEntity.getImgPath());
        //userVo.setPassword(userEntity.getPassword());
        userVo.setMobile(userEntity.getMobile());
        userVo.setCreateTime(DateUtils.format(userEntity.getCreateTime(), DateUtils.DATE_TIME_PATTERN));
        userVo.setDeptId(userEntity.getDeptId().toString());
        userVo.setDeptName(userEntity.getDeptName());
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
