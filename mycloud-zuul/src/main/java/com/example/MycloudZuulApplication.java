package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
/*@EnableDiscoveryClient
@EnableFeignClients*/
public class MycloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudZuulApplication.class, args);
	}
}

