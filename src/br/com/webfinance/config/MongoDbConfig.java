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
<<<<<<< HEAD
      Mongo mongo = new Mongo("127.8.18.1", 27017);
=======
      Mongo mongo = new Mongo("127.0.0.1", 27017);
>>>>>>> eb53e2049fff21abf19c335be66c6205ed79071b
     String databaseName = "finance";
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo,
                databaseName);
     return mongoDbFactory;
 }
 
<<<<<<< HEAD
}
=======
}
>>>>>>> eb53e2049fff21abf19c335be66c6205ed79071b
