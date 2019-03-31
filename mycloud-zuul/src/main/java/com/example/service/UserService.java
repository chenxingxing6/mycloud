package com.example.service;


import com.example.entity.SysUserEntity;
import com.example.form.LoginForm;

import java.util.Map;

/**
 * 用户
 */
public interface UserService{

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
