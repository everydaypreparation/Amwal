package com.advantal.adminRoleModuleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class AdminRoleModuleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminRoleModuleServiceApplication.class, args);
		System.out.print("---------------- Amwal Application Started --------------------");
	}
 
}


