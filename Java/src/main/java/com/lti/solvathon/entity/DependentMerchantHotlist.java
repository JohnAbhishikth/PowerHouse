package com.lti.solvathon.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dependent_merchant_hotlist")
@NamedQuery(name = "DependentMerchantHotlist.findAll", query = "SELECT d FROM DependentMerchantHotlist d")
public class DependentMerchantHotlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DependentMerchantHotlistPK id;

	@Column(name = "Alert_FLag")
	private String alertFLag;

	@Column(name = "Merchant_flag")
	private String merchantflag;

	@Column(name = "Spend_limit")
	private int spendlimit;

	// bi-directional many-to-one association to Dependent
	@ManyToOne
	@JoinColumn(name = "Dependent_Id", insertable = false, updatable = false)
	private Dependent dependent;

}