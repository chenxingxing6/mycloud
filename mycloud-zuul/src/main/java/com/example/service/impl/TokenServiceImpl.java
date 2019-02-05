package com.example.service.impl;

import com.example.common.exception.BizException;
import com.example.entity.SysUserEntity;
import com.example.service.TokenService;
import com.example.service.UserService;
import com.example.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    private final static int EXPIRE = 3600 * 6;//6小时过期

    @Override
    public String createToken(long userId) {
        //生成token
        String token = generateToken();
        SysUserEntity userEntity = userService.selectById(userId);
        if (userEntity == null) {
            throw new BizException("token生成失败!");
        }
        redisUtil.setObject(token, userEntity, EXPIRE);
        return token;
    }

    @Override
    public void cleanToken(String token) {
        redisUtil.delete(token);
    }

    @Override
    public SysUserEntity queryByToken(String token) {
        return (SysUserEntity) redisUtil.get(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
