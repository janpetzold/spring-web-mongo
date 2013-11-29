package de.janpetzold.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

@Configuration
@ComponentScan(basePackages = { "de.janpetzold.app.controller",
		"de.janpetzold.app.service", "de.janpetzold.app.repository" })
@EnableMongoRepositories(basePackages = { "de.janpetzold.app.repository" })
public class DbConfig extends AbstractMongoConfiguration {
	private static final String DB = "app";

	@Override
	public Mongo mongo() throws Exception {
		/* 
		 * We have a replica set of three servers - add the all, since any of them 
		 * might be become the (new) primary server.
		 */
		List<ServerAddress> servers = new ArrayList<>();
		servers.add(new ServerAddress("localhost", 30000));
		servers.add(new ServerAddress("localhost", 30001));
		servers.add(new ServerAddress("localhost", 30002));
		
		MongoOptions options = new MongoOptions();
		options.connectionsPerHost = 512;
		
		return new Mongo(servers, options);
	}

	@Override
	protected String getDatabaseName() {
		return DB;
	}
}
