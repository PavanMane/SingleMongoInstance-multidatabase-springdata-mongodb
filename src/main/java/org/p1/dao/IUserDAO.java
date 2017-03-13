package org.p1.dao;

import java.util.List;

public interface IUserDAO {
	
	List<User> findByFirstName(String firstName);

	User findOneByLoginName(String loginName);

	User save(User user);

	void deleteByLoginName(String loginName);

	User getByLoginName(String loginName);

	User update(User user);

	void delete(User user);

}
