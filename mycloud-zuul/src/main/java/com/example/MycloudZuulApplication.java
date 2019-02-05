package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
/*@EnableDiscoveryClient
@EnableFeignClients*/
@MapperScan(basePackages = {"com.example.dao"})
public class MycloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudZuulApplication.class, args);
	}
}

