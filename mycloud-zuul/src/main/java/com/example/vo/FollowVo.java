package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cxx
 * @Date: 2019/4/13 12:13
 */
@Data
public class FollowVo implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 是否被关注
     */
    private String isfollow;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 头像
     */
    private String imgPath;

    /**
     * 部门名称
     */
    private String deptName;
}
