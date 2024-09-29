package com.softdev.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
//@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
		log.info("Powered by https://zhengkai.blog.csdn.net/ ");
	}

}
