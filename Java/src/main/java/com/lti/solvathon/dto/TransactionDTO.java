package com.lti.solvathon.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionDTO {
	
	private int transactionId;
	private String dependentId;
	private String creditAccount;
	private String debitAccount;
	private int transactionAmount;
	private Timestamp transactionDate = new Timestamp(System.currentTimeMillis());
	private String status;
	private String transactionType;
	private String remark;
	
}
