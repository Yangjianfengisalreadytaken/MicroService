package com.tct.feginconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"tct.interfaces","com.tct.feginconsumer"})	//扫描其他module中的实体
public class FeginConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeginConsumerApplication.class, args);
	}
}
