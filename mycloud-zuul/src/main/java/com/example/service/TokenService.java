package com.example.service;

import com.example.entity.SysUserEntity;

/**
 * 用户Token
 */
public interface TokenService{

	/**
	 * 生成token
	 * @param userId  用户ID
	 * @return        返回token信息
	 */
	String createToken(long userId);

	/**
	 * 清除token
	 * @param token
	 * @return
	 */
	void cleanToken(String token);

	/**
	 * 查询token对应用户信息
	 * @param token
	 * @return
	 */
	SysUserEntity queryByToken(String token);
}
