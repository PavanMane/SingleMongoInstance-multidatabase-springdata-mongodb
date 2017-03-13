package org.p1.service;

import org.p1.dto.UserDTO;

public interface IUserService {

	void saveUser(UserDTO userDTO) throws Exception;
	UserDTO getUser(String loginName) throws Exception;
	void updateUser(UserDTO userDTO) throws Exception;
	void deleteUser(String loginName) throws Exception;
}
