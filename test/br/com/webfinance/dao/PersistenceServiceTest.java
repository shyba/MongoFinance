package br.com.webfinance.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.webfinance.config.DevMongoDBFactoryConfig;
import br.com.webfinance.config.MongoDbConfig;
import br.com.webfinance.model.Contact;
import br.com.webfinance.repo.ContactRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DevMongoDBFactoryConfig.class,MongoDbConfig.class })
@ActiveProfiles("dev")
public class PersistenceServiceTest {
	
	@Autowired
	ContactRepository repo;

	@Test
	public void test() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/bean.xml");
		System.out.println(repo.findAll());
		;
	}

}
