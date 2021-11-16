package com.spring.bank;

import com.spring.bank.entities.User;
import com.spring.bank.enums.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BankApplication {


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
		User user = new User();
		user.setRole(Role.ADMIN);
	}

}
