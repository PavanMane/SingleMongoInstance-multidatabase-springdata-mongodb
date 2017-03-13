package org.p1.dao.impl;

import java.util.List;

import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements IUserDAO {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findByFirstName(String firstName) {
		return userRepository.findByFirstName(firstName);
	}

	@Override
	public User findOneByLoginName(String loginName) {
		return userRepository.findOneByLoginName(loginName);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteByLoginName(String loginName) {
		userRepository.deleteByLoginName(loginName);
	}

	@Override
	public User getByLoginName(String loginName) {
		return userRepository.getByLoginName(loginName);
	}

	@Override
	public User update(User user) {
		User _userInDB = userRepository.findOneByLoginName(user.getLoginName());
		_userInDB.setDob(user.getDob());
		_userInDB.setFirstName(user.getFirstName());
		_userInDB.setMiddleName(user.getMiddleName());
		_userInDB.setLastName(user.getLastName());
		return userRepository.save(_userInDB);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	protected UserRepository getUserRepository() {
		return this.userRepository;
	}
}
