package de.janpetzold.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mongodb.Mongo;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "de.janpetzold.app.controller",
		"de.janpetzold.app.service", "de.janpetzold.app.repository" })
@EnableMongoRepositories(basePackages = { "de.janpetzold.app.repository" })
public class WebConfig extends AbstractMongoConfiguration {
	private static final String DB = "app";

	@Override
	public Mongo mongo() throws Exception {
		return new Mongo();
	}

	@Override
	protected String getDatabaseName() {
		return DB;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}
