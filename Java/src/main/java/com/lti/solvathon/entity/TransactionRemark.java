package com.lti.solvathon.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the transaction_remarks database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_remarks")
@NamedQuery(name = "TransactionRemark.findAll", query = "SELECT t FROM TransactionRemark t")
public class TransactionRemark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int transaction_id;

	@Column(name = "Transaction_Remarks")
	private String transactionRemarks;

	// bi-directional one-to-one association to Transaction
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Transaction_id")
	private Transaction transaction;

	@Override
	public String toString() {
		return "TransactionRemark [transaction_id=" + transaction_id + ", transactionRemarks=" + transactionRemarks
				+ "]";
	}

}