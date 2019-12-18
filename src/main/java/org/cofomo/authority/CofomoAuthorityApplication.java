package org.cofomo.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages ="org.cofomo.commons")
public class CofomoAuthorityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofomoAuthorityApplication.class, args);
	}

}
