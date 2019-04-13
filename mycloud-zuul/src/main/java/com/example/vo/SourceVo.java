package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: cxx
 * @Date: 2019/4/13 12:04
 */
@Data
public class SourceVo implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 文件父ID
     */
    private String parentId;

    /**
     * 文件名 test.html
     */
    private String originalName;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * "/"
     */
    private String originalPath;

    /**
     * 文件大小
     */
    private String length;

    /**
     * 文件类型（文件，文件夹）
     */
    private String type;

    /**
     * 是否可用看
     */
    private String viewFlag;

    /**
     * 下周次数
     */
    private String downloads;

    /**
     * 文件url
     */
    private String fileUrl;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String optTime;
}
