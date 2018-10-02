package com.tech.chefs.rest.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech.chefs.integration.Response;
import com.tech.chefs.service.UserService;



/**
 * @author jagadeeshnm
 * 
 * Test data has been given on the TestData.txt in the resources folder please refer to that to test all api's
 *
 */
@RestController
@RequestMapping(value="/api")
public class UserController {
	
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/ping" ,method=RequestMethod.GET)
	public String ping() {
		return "ping";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<Response> registerUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		logger.info("registering new user with username="+userName );
		return userService.register(userName, password);
	}
	

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<Response> loginUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
		logger.info("user "+ userName+ " accessing system login");
		return userService.login(userName, password);
	}
	

	@RequestMapping(value="/getBankList",method=RequestMethod.GET)
	public ResponseEntity<Response> getUserBankList(@RequestParam("userName") String userName) {
		logger.info("fetching banklist for user "+ userName);
		return userService.getBankList(userName);
	}
	
	@RequestMapping(value="/geBankData/{bankName}",method=RequestMethod.GET)
	public ResponseEntity<Response> getUserBankMeta(@PathVariable("bankName") String bankName) {
		logger.info("fetching metadata for bank" + bankName);
		return userService.getBankMetaData(bankName);
	}
	
	@RequestMapping(value="/logintobank/{bankName}",method=RequestMethod.POST)
	public ResponseEntity<Response> loginUserToBank(@PathVariable("bankName") String bankName, @RequestParam("userName") String userName, 
			@RequestParam("password") String password,@RequestParam("corpId") String corpId) {
		logger.info("user "+userName+" accessing banklogin for bank "+ bankName);
		return userService.loginToBank(bankName, userName, password, corpId);
	}
	
	@RequestMapping(value="/getAccounts/{bankName}",method=RequestMethod.GET)
	public ResponseEntity<Response> getBankAccountsList(@PathVariable("bankName") String bankName) {
		logger.info("fetching accounts information of bank "+bankName);
		return userService.getBankAccounts(bankName);
	}
	
	@RequestMapping(value="/getTransactionData/{accountNumber}",method=RequestMethod.GET)
	public ResponseEntity<Response> getAccountTransactionData(@PathVariable("accountNumber") String accountNumber) {
		logger.info("fetching transaction data for account number "+accountNumber);
		return userService.getTransactionData(accountNumber);
	}
	
	
	
}
