package com.tech.chefs.domain;

import java.util.List;

public class User {

	private String userName;
	
	private String password;
	
	private List<String> bankList;

	public List<String> getBankList() {
		return bankList;
	}

	public void setBankList(List<String> bankList) {
		this.bankList = bankList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
