package com.example.init;

import com.example.MycloudErurekaApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @auther lanxinghua
 * @date 2018/9/11
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MycloudErurekaApplication.class);
    }
}
