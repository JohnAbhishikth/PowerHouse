package com.lti.solvathon.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the transactions database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
@NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Transaction_id")
	private int transactionid;

	@Column(name = "Credit_Account")
	private String creditAccount;

	@Column(name = "Debit_Account")
	private String debitAccount;

	@Column(name = "Transaction_Amount")
	private int transactionAmount;

	@Column(name = "Transaction_Date")
	private Timestamp transactionDate = new Timestamp(System.currentTimeMillis());

	@Column(name = "Transaction_Status")
	private String transactionStatus;

	@Column(name = "Transaction_Type")
	private String transactionType;

	// bi-directional one-to-one association to TransactionRemark
	@OneToOne(mappedBy = "transaction")
	private TransactionRemark transactionRemark;

	public Transaction(String creditAccount, String debitAccount, int transactionAmount, String transactionStatus,
			String transactionType) {
		super();
		this.creditAccount = creditAccount;
		this.debitAccount = debitAccount;
		this.transactionAmount = transactionAmount;
		this.transactionStatus = transactionStatus;
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionid=" + transactionid + ", creditAccount=" + creditAccount + ", debitAccount="
				+ debitAccount + ", transactionAmount=" + transactionAmount + ", transactionDate=" + transactionDate
				+ ", transactionStatus=" + transactionStatus + ", transactionType=" + transactionType + "]";
	}

}