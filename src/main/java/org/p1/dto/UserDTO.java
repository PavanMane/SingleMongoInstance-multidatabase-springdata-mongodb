package org.p1.dto;

import java.util.List;

public class UserDTO {

	private String firstName;
	private String middleName;
	private String lastName;
	private String loginName;
	private String dob;
	private List<BottleDTO> bottles;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public List<BottleDTO> getBottles() {
		return bottles;
	}
	public void setBottles(List<BottleDTO> bottles) {
		this.bottles = bottles;
	}
	
}
