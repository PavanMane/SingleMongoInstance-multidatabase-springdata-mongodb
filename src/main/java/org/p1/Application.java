package org.p1;

import org.p1.config.MongoDBCredentials;
import org.p1.config.MongoTenantTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@ComponentScan(basePackages = { "org.p1.config", "org.p1.controller", "org.p1.dao", "org.p1.service.impl" })
public class Application {

	@Autowired
	public MongoDBCredentials mongoDBCredentials;

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTenantTemplate(
				new SimpleMongoDbFactory(new MongoClient(new MongoClientURI(mongoDBCredentials.getUri())),
						mongoDBCredentials.getDefaultDatabaseName()));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
