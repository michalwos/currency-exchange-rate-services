package com.hcl.currencyexchange.alert.demo;

import com.hcl.currencyexchange.alert.demo.service.DemoAlertSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class DemoAlertSetupServiceApplication {

	@Autowired
	private DemoAlertSetupService demoAlertSetupService;

	@PostConstruct
	public void createUserSettings() {
		demoAlertSetupService.createUserSettings();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoAlertSetupServiceApplication.class, args);
	}

}
