package com.tech.chefs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tech.chefs.domain.AccountInfo;
import com.tech.chefs.domain.Bank;
import com.tech.chefs.domain.BankAuthentication;
import com.tech.chefs.domain.TransactionData;
import com.tech.chefs.domain.User;
import com.tech.chefs.integration.AccountInfoResponse;
import com.tech.chefs.integration.BankMetaResponse;
import com.tech.chefs.integration.Response;
import com.tech.chefs.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	private Map<String,User> userMap = new HashMap<String,User>();
	
	private Map<String,Boolean> userSession = new HashMap<String,Boolean>();
	
	private Map<String,Bank> bankMap = new HashMap<String,Bank>();
	
	private Map<String,BankAuthentication> bankAuthMap = new HashMap<String,BankAuthentication>();
	
	private Map<String,Boolean> bankSession = new HashMap<String,Boolean>();
	
	List<AccountInfo> accountInfoList = new ArrayList<AccountInfo>();
	
	private Map<String,List<AccountInfo>> accountsMap = new HashMap<String,List<AccountInfo>>();
	
	@PostConstruct
	public void init() {
		/*hard coding all the necessary data*/
		List<Bank> bankList = new ArrayList<Bank>();
		List<BankAuthentication> bankAuthList = new ArrayList<BankAuthentication>();
		Bank bank = new Bank();
		BankAuthentication bankAuth = new BankAuthentication();
		
		bank.setCorpId("1");
		bank.setName("HDFC");
		bankList.add(bank);
		bankAuth.setName("HDFC");
		bankAuth.setUserName("YES");
		bankAuth.setPassword("YES");
		bankAuth.setCorpId("YES");
		bankAuthList.add(bankAuth);
		
		bank = new Bank();
		bank.setCorpId("2");
		bank.setName("AXIS");
		bankList.add(bank);
		bankAuth = new BankAuthentication();
		bankAuth.setName("AXIS");
		bankAuth.setUserName("YES");
		bankAuth.setPassword("NO");
		bankAuth.setCorpId("NO");
		bankAuthList.add(bankAuth);
		
		for(Bank newBank : bankList) {
			bankMap.put(newBank.getName(), newBank);
		}
		
		for(BankAuthentication newBankAuth : bankAuthList) {
			bankAuthMap.put(newBankAuth.getName(), newBankAuth);
		}
		
		List<AccountInfo> accountsList = new ArrayList<AccountInfo>();
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountNumber("1234556789");
		accountInfo.setAccountType("savings");
		accountsList.add(accountInfo);
		
		accountInfo = new AccountInfo();
		accountInfo.setAccountNumber("9876543210");
		accountInfo.setAccountType("current");
		accountsList.add(accountInfo);
		accountsMap.put("HDFC", accountsList);
		
		accountInfoList.addAll(accountsList);
		
		accountsList = new ArrayList<AccountInfo>();
		accountInfo = new AccountInfo();
		accountInfo.setAccountNumber("546464546459");
		accountInfo.setAccountType("savings");
		accountsList.add(accountInfo);
		
		accountInfo = new AccountInfo();
		accountInfo.setAccountNumber("282726345456");
		accountInfo.setAccountType("current");
		accountsList.add(accountInfo);
		accountsMap.put("AXIS", accountsList);
		
		accountInfoList.addAll(accountsList);
	}
	
	@Override
	public ResponseEntity<Response> register(String userName, String password) {
		// TODO Auto-generated method stub
		Response errorMsg = new Response();
		if(userMap.get(userName) != null) {
			errorMsg.setErrorCode("10");
			errorMsg.setErrorMsg("user already exists");
			return new ResponseEntity<Response>(errorMsg,HttpStatus.ALREADY_REPORTED);
		}
		
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		userMap.put(userName, user);
		errorMsg.setErrorCode("20");
		errorMsg.setErrorMsg("registered successfully");
		return new ResponseEntity<Response>(errorMsg, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> login(String userName, String password) {
		Response errorMsg = new Response();
		if(userMap.get(userName) == null) {
			errorMsg.setErrorCode("10");
			errorMsg.setErrorMsg("You are not a valid user please register");
			return new ResponseEntity<Response>(errorMsg,HttpStatus.EXPECTATION_FAILED);
		}
		User loggedinUser = userMap.get(userName);
		if(loggedinUser != null && !loggedinUser.getPassword().equals(password)) {
				errorMsg.setErrorCode("10");
				errorMsg.setErrorMsg("Your password is wrong");
				return new ResponseEntity<Response>(errorMsg,HttpStatus.EXPECTATION_FAILED);
		}
		
		userSession.put(loggedinUser.getUserName(), true);
		errorMsg.setErrorCode("20");
		errorMsg.setErrorMsg("You are logged In");
		return new ResponseEntity<Response>(errorMsg,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getBankList(String userName) {
		Response response = new Response();
		if(userSession.get(userName) == null  || !userSession.get(userName)) {
			response.setErrorCode("10");
			response.setErrorMsg("You are not logged In");
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}
		List<Bank> bankList = new ArrayList<Bank>();
		Map.Entry<String, Bank> val = null;
		Iterator<Entry<String, Bank>> it = bankMap.entrySet().iterator();
		while(it.hasNext()) {
			val = (Map.Entry<String, Bank>) it.next();
			bankList.add(val.getValue());
		}
		
		response.setBanklist(bankList);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Response> getBankMetaData(String bankName) {
		BankMetaResponse response = new BankMetaResponse();
		BankAuthentication  bankAuthentication = bankAuthMap.get(bankName);
		response.setUserName(bankAuthentication.getUserName());
		response.setPassword(bankAuthentication.getPassword());
		response.setCorpId(bankAuthentication.getCorpId());
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> loginToBank(String bankName, String userName, String password, String corpId) {
		Response response = new Response();
		BankAuthentication  bankAuthentication = bankAuthMap.get(bankName);
		
		if(bankAuthentication.getUserName().equals("YES") && (userName == null || userName.equals(""))) {
			response.setErrorCode("10");
			response.setErrorMsg("You Are Not Authorized to login " + bankName);
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}else if(bankAuthentication.getPassword().equals("YES") && (password == null || password.equals(""))) {
			response.setErrorCode("10");
			response.setErrorMsg("You Are Not Authorized to login " + bankName);
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}else if(bankAuthentication.getCorpId().equals("YES") && (corpId == null || corpId.equals(""))) {
			response.setErrorCode("10");
			response.setErrorMsg("You Are Not Authorized to login to "+ bankName);
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}
		
		bankSession.put(bankName, true);
		
		response.setErrorCode("20");
		response.setErrorMsg("You are logged In");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getBankAccounts(String bankName) {
		AccountInfoResponse response = new AccountInfoResponse();
		if(bankSession.get(bankName) == null  || !bankSession.get(bankName)) {
			response.setErrorCode("10");
			response.setErrorMsg("You have not logged into " + bankName);
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}
		
		response.setAccountsInfo(accountsMap.get(bankName));
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getTransactionData(String accountNumber) {
		AccountInfoResponse response = new AccountInfoResponse();
		Boolean isAccountExists = false;
		for(AccountInfo accountInfo : accountInfoList) {
			if(accountNumber.equals(accountInfo.getAccountNumber()))
				isAccountExists = true;
		}
		
		if(!isAccountExists) {
			response.setErrorCode("10");
			response.setErrorMsg("No such account information");
			return new ResponseEntity<Response>(response,HttpStatus.EXPECTATION_FAILED);
		}
		
		TransactionData transactionData = new TransactionData();
		List<TransactionData> transactionDataList = new ArrayList<TransactionData>();
		for(int i=0;i<=2;i++) {
			if(i%2 == 0) {
				transactionData = new TransactionData();
				transactionData.setAccountNumber(accountNumber);
				transactionData.setTimestamp(new Date());
				transactionData.setType("CREDIT");
				transactionData.setAmount(10000);
				transactionDataList.add(transactionData);
			} else {
				transactionData = new TransactionData();
				transactionData.setAccountNumber(accountNumber);
				transactionData.setTimestamp(new Date());
				transactionData.setType("DEBIT");
				transactionData.setAmount(500);
				transactionDataList.add(transactionData);
			}
		}
		
		response.setTransactionData(transactionDataList);
		
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	
}
