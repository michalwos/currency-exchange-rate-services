package com.hcl.currencyexchange.fcimportservice;

import com.hcl.currencyexchange.fcimportservice.config.ProducerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class FcImportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcImportServiceApplication.class, args);
	}

}
