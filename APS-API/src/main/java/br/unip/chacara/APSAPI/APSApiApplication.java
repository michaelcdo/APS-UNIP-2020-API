package br.unip.chacara.APSAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.unip.chacara.EIAAPI")
@EntityScan(basePackages = "br.unip.chacara.EIAAPI.model")
public class APSApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(APSApiApplication.class, args);
	}

}
