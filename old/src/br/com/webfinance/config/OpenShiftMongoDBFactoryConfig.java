package br.com.webfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

@Component(value="mongoDbFactoryConfig")
@Profile("openshift")
public class OpenShiftMongoDBFactoryConfig implements MongoDbFactoryConfig {
 
    @Override
  public MongoDbFactory mongoDbFactory() throws Exception {
      String openshiftMongoDbHost = System.getenv("OPENSHIFT_NOSQL_DB_HOST");
      int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_NOSQL_DB_PORT"));
       String username = System.getenv("OPENSHIFT_NOSQL_DB_USERNAME");
      String password = System.getenv("OPENSHIFT_NOSQL_DB_PASSWORD");
      Mongo mongo = new Mongo(openshiftMongoDbHost, openshiftMongoDbPort);
       UserCredentials userCredentials = new UserCredentials(username,password);
      String databaseName = "MongoFinanceTC";
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
        return mongoDbFactory;
 }
 
}