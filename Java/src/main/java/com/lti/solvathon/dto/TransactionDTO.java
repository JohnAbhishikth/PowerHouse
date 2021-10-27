package com.lti.solvathon.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
