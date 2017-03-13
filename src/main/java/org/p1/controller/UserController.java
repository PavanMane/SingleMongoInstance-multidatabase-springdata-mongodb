package org.p1.controller;

import org.p1.TenantContext;
import org.p1.dto.UserDTO;
import org.p1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/v1/user")
public class UserController {
	
	@Autowired
	private IUserService userService; 
	
	@RequestMapping(value = "/{loginName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<? extends Object> getUser(@PathVariable String loginName, @RequestParam String tenantCode) {
		TenantContext.setTenant(tenantCode);
		try {
			return new ResponseEntity<UserDTO>(userService.getUser(loginName), HttpStatus.OK);
		} catch (Exception e) {
			BaseResponse response = new BaseResponse();
			response.setCode(ResponseCode.ERROR);
			response.setMessage(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
}
