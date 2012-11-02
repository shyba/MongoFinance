package br.com.webfinance.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

	private static MongoOperations mongo;

	public static MongoOperations getMongoInstance() {
		return mongo;
	}

	@Autowired
	public void setMongo(MongoOperations mongo) {
		PersistenceService.mongo = mongo;

	}


}
