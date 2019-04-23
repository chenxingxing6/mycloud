package com.example.feign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


/**
 * User: lanxinghua
 * Date: 2019/4/14 10:54
 * Desc: 上传文件服务
 */
@FeignClient(value = "service", configuration = IUploadService.FeignConfig.class, url = "http://localhost:9000")
public interface IUploadService {
    /**
     * 更新头像
     * @param file
     * @return
     */
    @PostMapping(value = "/updateImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String updateImg(@RequestPart(value = "file") MultipartFile file);


    public class FeignConfig {
        @Bean
        @Primary
        @Scope("prototype")
        public Encoder multipartFormEncoder() {
            return new SpringFormEncoder();
        }

        @Bean
        public feign.Logger.Level multipartLoggerLevel() {
            return feign.Logger.Level.FULL;
        }

    }
}
