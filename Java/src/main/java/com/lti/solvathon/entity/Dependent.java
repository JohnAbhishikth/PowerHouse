package com.lti.solvathon.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Dependent.findAll", query = "SELECT d FROM Dependent d")
public class Dependent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "dependent_Id")
	private String dependentId;

	@Column(name = "dependent_Age")
	private int dependentAge;

	@Column(name = "dependent_Email")
	private String dependentEmail;

	@Column(name = "dependent_Gender")
	private String dependentGender;

	@Column(name = "dependent_Mobile")
	private String dependentMobile;

	@Column(name = "dependent_Name")
	private String dependentName;

	@Column(name = "dependent_Password")
	private String dependentPassword;

	@OneToMany(mappedBy = "dependent")
	private Set<DependentMerchantHotlist> dependentMerchantHotlists;

	@OneToOne(mappedBy = "dependent")
	private DependentSpendLimit dependentSpendLimit;

	@OneToOne(mappedBy = "dependent")
	private MasterDependent masterDependent;

	public DependentMerchantHotlist addDependentMerchantHotlist(DependentMerchantHotlist dependentMerchantHotlist) {
		getDependentMerchantHotlists().add(dependentMerchantHotlist);
		dependentMerchantHotlist.setDependent(this);

		return dependentMerchantHotlist;
	}

	public DependentMerchantHotlist removeDependentMerchantHotlist(DependentMerchantHotlist dependentMerchantHotlist) {
		getDependentMerchantHotlists().remove(dependentMerchantHotlist);
		dependentMerchantHotlist.setDependent(null);

		return dependentMerchantHotlist;
	}

	public Dependent(String dependentId, int dependentAge, String dependentEmail, String dependentGender,
			String dependentMobile, String dependentName, String dependentPassword) {
		super();
		this.dependentId = dependentId;
		this.dependentAge = dependentAge;
		this.dependentEmail = dependentEmail;
		this.dependentGender = dependentGender;
		this.dependentMobile = dependentMobile;
		this.dependentName = dependentName;
		this.dependentPassword = dependentPassword;
	}

	@Override
	public String toString() {
		return "Dependent [dependentId=" + dependentId + ", dependentAge=" + dependentAge + ", dependentEmail="
				+ dependentEmail + ", dependentGender=" + dependentGender + ", dependentMobile=" + dependentMobile
				+ ", dependentName=" + dependentName + ", dependentPassword=" + dependentPassword + "]";
	}

}
