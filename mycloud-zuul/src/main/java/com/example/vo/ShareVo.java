package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cxx
 * @Date: 2019/4/13 12:08
 */
@Data
public class ShareVo implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 分享用户ID
     */
    private String fromUserId;

    /**
     * 分享用户名
     */
    private String fromUserName;

    /**
     * 发送用户ID
     */
    private String toUserId;

    /**
     * 发送用户名
     */
    private String toUserName;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件过期时间
     */
    private String expiredTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;
}
