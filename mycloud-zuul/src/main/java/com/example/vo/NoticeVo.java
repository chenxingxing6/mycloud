package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cxx
 * @Date: 2019/4/13 11:40
 */
@Data
public class NoticeVo implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 公共图片头像
     */
    private String img;


}
