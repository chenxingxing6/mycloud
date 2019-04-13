package com.example.service.impl;

import com.example.common.exception.BizException;
import com.example.entity.SysUserEntity;
import com.example.feign.IUserService;
import com.example.service.TokenService;
import com.example.utils.RedisKeys;
import com.example.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;


@Service("tokenService")
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUserService userService;

    private final static int EXPIRE = 3600 * 6;//6小时过期

    @Override
    public String createToken(long userId) {
        //生成token
        String token = generateToken();
        SysUserEntity userEntity = userService.getUserByUserId(String.valueOf(userId));
        if (StringUtils.isEmpty(token) || userEntity == null) {
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

    //生成短信验证码
    private int generateCapatcha(){
        Random random = new Random();
        int max = 999999;// 最大值
        int min = 100000;// 最小值
        int code = random.nextInt(max);// 随机生成
        if(code < min){// 低于6位数，加上最小值，补上
            code = code + min;
        }
        return code;
    }

    @Override
    public String createCaptcha(String mobile) {
        String key = RedisKeys.getCapatchaKey(mobile);
        Object result = redisUtil.get(key);
        if (result == null){
            String capathca = String.valueOf(generateCapatcha());
            redisUtil.setObject(key, capathca, 60);
            return capathca;
        }
        return result.toString();
    }

    @Override
    public boolean checkCaptcha(String mobile, String captcha) {
        String key = RedisKeys.getCapatchaKey(mobile);
        Object result = redisUtil.get(key);
        if (result == null){
           return false;
        }
        return result.toString().equals(captcha);
    }
}
