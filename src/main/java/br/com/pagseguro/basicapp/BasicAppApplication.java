package br.com.pagseguro.basicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BasicAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicAppApplication.class, args);
	}

}
