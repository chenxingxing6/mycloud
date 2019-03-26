package com.example.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.common.exception.BizException;
import com.example.common.validator.Assert;
import com.example.dao.UserDao;
import com.example.entity.SysUserEntity;
import com.example.form.LoginForm;
import com.example.service.TokenService;
import com.example.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, SysUserEntity> implements UserService {
    @Autowired
    private TokenService tokenService;

    @Override
    public SysUserEntity queryByMobile(String mobile) {
        SysUserEntity user = new SysUserEntity();
        user.setMobile(mobile);
        return baseMapper.selectOne(user);
    }

    @Override
    public SysUserEntity queryByUserName(String userName) {
        SysUserEntity user = new SysUserEntity();
        user.setUsername(userName);
        return baseMapper.selectOne(user);
    }

    @Override
    public Map<String, Object> login(LoginForm form) {
        SysUserEntity user = queryByUserName(form.getUsername());
        Assert.isNull(user, "账号或密码错误");
        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new BizException("账号或密码错误");
        }
        //获取登录token
        String token = tokenService.createToken(user.getUserId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", token);
        return map;
    }
}
