package com.example.controller;


import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页数据接口
 */
@RestController
@RequestMapping("/api")
public class ApiNoticeController {
    @Autowired
    private TokenService tokenService;


}
