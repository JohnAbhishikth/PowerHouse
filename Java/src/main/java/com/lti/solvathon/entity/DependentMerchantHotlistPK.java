package com.lti.solvathon.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The primary key class for the dependent_merchant_hotlist database table.
 * 
 */
@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class DependentMerchantHotlistPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private String dependent_Id;

	private String merchant;

}