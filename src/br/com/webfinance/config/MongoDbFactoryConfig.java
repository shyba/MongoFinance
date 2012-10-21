package br.com.webfinance.config;

import org.springframework.data.mongodb.MongoDbFactory;

public interface MongoDbFactoryConfig {

	public abstract MongoDbFactory mongoDbFactory() throws Exception;

}