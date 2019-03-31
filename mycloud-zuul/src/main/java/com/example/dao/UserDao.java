package com.example.dao;

import com.example.entity.SysUserEntity;
import com.example.form.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xuyaohui
 * @date 2018/7/31 0031 上午 10:08
 */

@Repository
public interface UserDao {
    /**
     * 根据手机查用户信息
     * @param mobile
     * @return
     */
    SysUserEntity queryByMobile(String mobile);

    /**
     * 根据用户名查用户信息
     * @param userName
     * @return
     */
    SysUserEntity queryByUserName(String userName);

    /**
     * 用户登录
     * @param form    登录表单
     * @return        返回登录信息
     */
    Map<String, Object> login(LoginForm form);

}
