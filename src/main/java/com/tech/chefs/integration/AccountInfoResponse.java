package com.tech.chefs.integration;

import java.util.List;

import com.tech.chefs.domain.AccountInfo;
import com.tech.chefs.domain.TransactionData;

public class AccountInfoResponse extends Response {

	private List<AccountInfo> accountsInfo;
	
	private List<TransactionData> transactionData;

	public List<AccountInfo> getAccountsInfo() {
		return accountsInfo;
	}

	public void setAccountsInfo(List<AccountInfo> accountsInfo) {
		this.accountsInfo = accountsInfo;
	}

	public List<TransactionData> getTransactionData() {
		return transactionData;
	}

	public void setTransactionData(List<TransactionData> transactionData) {
		this.transactionData = transactionData;
	}
	
	
}
