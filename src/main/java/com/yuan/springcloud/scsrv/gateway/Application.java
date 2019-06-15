package com.yuan.springcloud.scsrv.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableEurekaClient
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableZuulProxy
@SpringBootApplication(exclude =
		{
				org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
				DataSourceAutoConfiguration.class,
				EurekaClientAutoConfiguration.class
		})
@MapperScan(basePackages = "com.yuan.springcloud.scsrv.gateway.dao.domain.**",sqlSessionFactoryRef = "sqlSessionFactory")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
