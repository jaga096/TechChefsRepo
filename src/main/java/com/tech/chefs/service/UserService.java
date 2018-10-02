package com.tech.chefs.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.tech.chefs.integration.Response;

public interface UserService {

	public abstract ResponseEntity<Response> register(String userName, String password);
	
	public abstract ResponseEntity<Response> login(String userName, String password);
	
	public ResponseEntity<Response> getBankList(String userName);
	
	public ResponseEntity<Response> getBankMetaData(String bankName);
	
	public abstract ResponseEntity<Response> loginToBank(String bankName, String userName, String password, String corpId);
	
	public abstract ResponseEntity<Response> getBankAccounts(@PathVariable("bankName") String bankName);
	
	public abstract ResponseEntity<Response> getTransactionData(@PathVariable("accountNumber") String accountNumber);
}
