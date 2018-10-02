package com.tech.chefs.integration;

import java.util.List;
import java.util.Map;

import com.tech.chefs.domain.Bank;

public class Response {

	private String errorCode;
	private String ErrorMsg;
	private List<Bank> banklist;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	public List<Bank> getBanklist() {
		return banklist;
	}
	public void setBanklist(List<Bank> banklist) {
		this.banklist = banklist;
	}
	
	
}
