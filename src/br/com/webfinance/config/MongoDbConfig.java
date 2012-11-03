package br.com.webfinance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ImportResource("classpath:mongo.xml")
public class MongoDbConfig {
	
	@Autowired
	MongoDbFactoryConfig mongoDbFactoryConfig;
	
	
//	public MongoDbConfig(MongoDbFactoryConfig mongoDbFactoryConfig){
//		this.mongoDbFactoryConfig=mongoDbFactoryConfig;
//	}
	
    
 
    @Bean(name="mongoTemplate")
  public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactoryConfig.mongoDbFactory());
        return mongoTemplate;
  }
 
}

//package br.com.webfinance.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//
//import com.mongodb.Mongo;
//
//@Configuration
//@Profile("dev")
//public class MongoDbConfig {
// 
//    @Bean
//  public MongoTemplate mongoTemplate() throws Exception {
//        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
//     return mongoTemplate;
//  }
// 
// @Bean
//  public MongoDbFactory mongoDbFactory() throws Exception {
//      Mongo mongo = new Mongo("127.0.0.1", 27017);
//     String databaseName = "finance";
//        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo,
//                databaseName);
//     return mongoDbFactory;
// }
// 
//}
//
