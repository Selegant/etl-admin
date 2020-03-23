package com.selegant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.selegant.kettle.mapper")
public class KettleJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(KettleJobApplication.class, args);
	}

}
