package io.github.ruben;

import io.github.ruben.file.application.FileService;
import io.github.ruben.file.infraestructure.configuration.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Db1Application {

	public static void main(String[] args) {
		SpringApplication.run(Db1Application.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
			}
		};
	}

	@Bean
	CommandLineRunner init(FileService fileService) {
		return (args) -> {
			fileService.deleteAll();
			if(args.length == 0){
				fileService.init(null);
			}else{
				fileService.init(args[0]);
			}
		};
	}

}
