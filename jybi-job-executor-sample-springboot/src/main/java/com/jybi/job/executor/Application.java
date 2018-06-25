package com.jybi.job.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
//@EnableConfigurationProperties(DataSourceConfig.class)
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}

}