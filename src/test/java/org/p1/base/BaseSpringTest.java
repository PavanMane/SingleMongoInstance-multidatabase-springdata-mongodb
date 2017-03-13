package org.p1.base;

import static org.springframework.util.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(value="classpath:application-test.properties")
public class BaseSpringTest implements ApplicationContextAware {
	
	protected ApplicationContext applicationContext;

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@Test
	public void testing() {
		System.out.println("***** Executing Test cases on DB: " + mongoTemplate.getDb().getName() + ", *****");
		notNull(mongoTemplate);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
