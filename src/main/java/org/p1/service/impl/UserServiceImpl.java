package org.p1.service.impl;

import org.p1.dao.IUserDAO;
import org.p1.dao.User;
import org.p1.dto.UserDTO;
import org.p1.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void saveUser(UserDTO userDTO) {
		userDAO.save(copyFromDTO(userDTO));
	}

	@Override
	public UserDTO getUser(String loginName) throws Exception {
		System.out.println("Database used ********** ---------->>>>>>>>>> " + mongoTemplate.getDb().getName());
		User user = userDAO.getByLoginName(loginName);
		if(user == null) {
			throw new  Exception("User not found with loginName: " + loginName);
		}
		return copyToDTO(user);
	}

	@Override
	public void updateUser(UserDTO userDTO) throws Exception {
		userDAO.update(copyFromDTO(userDTO));
	}

	@Override
	public void deleteUser(String loginName) throws Exception {
		User user = userDAO.getByLoginName(loginName);
		if(user == null) {
			throw new  Exception("User not found with loginName: " + loginName);
		}
		userDAO.deleteByLoginName(loginName);
	}
	
	private User copyFromDTO(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user;
	}
	
	private UserDTO copyToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}

}
