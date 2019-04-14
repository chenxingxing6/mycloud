package com.example.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * User: lanxinghua
 * Date: 2019/4/14 21:00
 * Desc: 链接vo
 */
@Data
public class LinkVo implements Serializable {
    private String userName;

    private String userId;

    private String email;

    private String dept;

    private String imgPath;

    private String fileId;

    private String fileName;
}
