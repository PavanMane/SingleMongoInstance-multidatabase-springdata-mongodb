package org.p1.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;
import org.p1.TenantContext;
import org.p1.base.BaseSpringTest;
import org.p1.dao.Bottle;
import org.p1.dao.Bottle.Type;
import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexInfo;

public class UserDAOImplTest extends BaseSpringTest {

	@Autowired
	private IUserDAO userDAO;
	
	@BeforeClass
	public static void onLoad() {
		System.out.println("***** ----- Running UserDAOImplTest test cases .....");
	}
	
	@Test
	public void test() {
		String firstName = "Pavan";
		String middleName = "V";
		String loginName = "pavan.v.m@zzzzz.in";
		
		// Create user - in default database
		
		User user = getUser(loginName, firstName, middleName);
		user.setBottles(getBottles());
		userDAO.save(user);
		
		List<IndexInfo> indexInfos = mongoTemplate.indexOps(User.class).getIndexInfo();
		System.out.println("DB ---->" + mongoTemplate.getDb());
		for(IndexInfo idxInf : indexInfos) {
			System.out.println("Indexe ---> " + idxInf.getName());
			for(IndexField idxf: idxInf.getIndexFields()) {
				System.out.println("On Field ---> " + idxf.getKey());
			}
			
		}
		userDAO.delete(user);
		

		//	Create user - in tenant1 database
		TenantContext.setTenant("tenant1");
		user = getUser(loginName, firstName, middleName);
		user.setBottles(getBottles());
		userDAO.save(user);
		indexInfos = mongoTemplate.indexOps(User.class).getIndexInfo();
		System.out.println("DB ---->" + mongoTemplate.getDb());
		for(IndexInfo idxInf : indexInfos) {
			System.out.println("Index ---> " + idxInf.getName());
			for(IndexField idxf: idxInf.getIndexFields()) {
				System.out.println("On Field ---> " + idxf.getKey());
			}
			
		}
		userDAO.delete(user);
		
		
		//	Create user - in tenant2 database
		TenantContext.setTenant("tenant2");
		user = getUser(loginName, firstName, middleName);
		user.setBottles(getBottles());
		userDAO.save(user);
		indexInfos = mongoTemplate.indexOps(User.class).getIndexInfo();
		System.out.println("DB ---->" + mongoTemplate.getDb());
		for(IndexInfo idxInf : indexInfos) {
			System.out.println("Index ---> " + idxInf.getName());
			for(IndexField idxf: idxInf.getIndexFields()) {
				System.out.println("On Field ---> " + idxf.getKey());
			}
			
		}
		userDAO.delete(user);	
	}
	
	public static List<Bottle> getBottles() {
		List<Bottle> bottles = new ArrayList<>();
		String pattern = "CamelBack";
		
		Bottle bottle2 = new Bottle();
		String name2 = "Test" + pattern + UUID.randomUUID();
		bottle2.setName(name2);
		bottle2.setType(Type.SIPPER);
		bottles.add(bottle2);
		
		String name = pattern + UUID.randomUUID();
		Bottle bottle1 = new Bottle();
		bottle1.setName(name);
		bottle1.setType(Type.SIPPER);
		bottles.add(bottle1);
		
		return bottles;
	}
	
	public static User getUser(String loginName, String firstName, String middleName) {
		User user = new User();
		user.setFirstName(firstName);
		user.setMiddleName(middleName);
		user.setLastName("M");
		user.setLoginName(loginName);
		user.setDob(new Date());
		user.setBottles(UserDAOImplTest.getBottles());
		return user;
	}
}
