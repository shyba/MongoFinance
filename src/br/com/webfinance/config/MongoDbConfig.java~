package br.com.webfinance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
public class MongoDbConfig {
 
    @Bean
  public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
     return mongoTemplate;
  }
 
 @Bean
  public MongoDbFactory mongoDbFactory() throws Exception {
      Mongo mongo = new Mongo("127.8.18.1", 27017);
     String databaseName = "finance";
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo,
                databaseName);
     return mongoDbFactory;
 }
 
}
