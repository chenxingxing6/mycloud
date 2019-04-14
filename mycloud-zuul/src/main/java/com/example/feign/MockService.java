package com.example.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: lanxinghua
 * Date: 2019/4/12 09:51
 * Desc: RestTemplate方式请求http
 */
@Service
public class MockService {
    private static final String url = "https://result.eolinker.com/1DVzyqbab364c82aaead42f24de532c4c046e6805221c90?uri=/mock/";
    @Autowired
    private RestTemplate restTemplate;

    public String getData(String path){
        return restTemplate.getForObject(url + path, String.class);
    }
}
