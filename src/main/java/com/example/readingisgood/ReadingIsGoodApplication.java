package com.example.readingisgood;

import com.example.readingisgood.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@SpringBootApplication
public class ReadingIsGoodApplication {

	@Autowired
	private ConfigProperties appconf;

	@Bean
	@ConfigurationProperties
	public ConfigProperties configProperties(){
		return new ConfigProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(ReadingIsGoodApplication.class, args);
	}

}
