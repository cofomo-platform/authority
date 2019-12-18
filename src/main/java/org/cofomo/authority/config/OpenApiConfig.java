package org.cofomo.authority.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	
	public static String swaggerServerBasepath = "http://localhost:8082";
	public static String swaggerServerDescription = "This is the local test server";
	
	public  OpenApiConfig(@Value("${swagger.basepath}") String basepath, @Value("${swagger.description}") String description) {
		OpenApiConfig.swaggerServerBasepath = basepath;
		OpenApiConfig.swaggerServerDescription = description;
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
		
		Server server = new Server().url(OpenApiConfig.swaggerServerBasepath).description(OpenApiConfig.swaggerServerDescription);

		return new OpenAPI()
				.components(new Components()).addServersItem(server)
				.info(new Info().title("COFOMO Authority API").description(
				"This is a sample Spring Boot RESTful service implementing the cofomo-authority interface"));
	}

}
