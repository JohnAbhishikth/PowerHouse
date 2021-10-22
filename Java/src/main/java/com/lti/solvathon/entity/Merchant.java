package com.lti.solvathon.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the merchant database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m")
public class Merchant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Merchant_Id")
	private String merchantId;

	@Column(name = "Merchant_Name")
	private String merchantName;

	@Column(name = "Merchant_Type")
	private String merchantType;

}
