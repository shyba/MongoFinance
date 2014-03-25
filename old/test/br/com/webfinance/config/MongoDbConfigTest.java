//package br.com.webfinance.config;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import br.com.webfinance.repo.ContactRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:META-INF/bean.xml")
//public class MongoDbConfigTest {
//	
//	@Autowired
//	MongoTemplate mongoTemplate;
//
//	@Autowired
//	ContactRepository contactRepository;
//
//	@Test
//	public void shouldCheckIfContextIsLoadedProperly() {
//		assertNotNull(mongoTemplate);
//		assertNotNull(contactRepository);
//	}
//
//}