package com.example.controller;


import com.example.feign.MockService;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mock数据
 */
@RestController
@RequestMapping("/api")
public class ApiMockController {
    @Autowired
    private MockService mockService;

    @RequestMapping("/{name}")
    public String createShareUrl(@PathVariable String name){
        String result = mockService.getData(name);
        System.out.println("mock数据 name:"+result);
        return result;
    }
}
