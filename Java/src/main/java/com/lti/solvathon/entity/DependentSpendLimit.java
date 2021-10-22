package com.lti.solvathon.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the dependent_spend_limit database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "dependent_spend_limit")
@NamedQuery(name = "DependentSpendLimit.findAll", query = "SELECT d FROM DependentSpendLimit d")
public class DependentSpendLimit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String dependent_Id;

	@Column(name = "Account_Balance")
	private int accountBalance;

	@Column(name = "spend_flag")
	private String spendFlag;

	@Column(name = "Spend_limit")
	private int spendlimit;

	// bi-directional one-to-one association to Dependent
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Dependent_Id")
	private Dependent dependent;

}