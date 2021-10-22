package com.lti.solvathon.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the master database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Master.findAll", query = "SELECT m FROM Master m")
public class Master implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Master_Id")
	private String masterId;

	@Column(name = "Master_Acc_Bal")
	private int masterAccountBalance;

	@Column(name = "Master_Email")
	private String masterEmail;

	@Column(name = "Master_Gender")
	private String masterGender;

	@Column(name = "Master_Mobile")
	private String masterMobile;

	@Column(name = "Master_Name")
	private String masterName;

	@Column(name = "Master_Password")
	private String masterPassword;
	
	// bi-directional many-to-one association to MasterDependent
	@OneToMany(mappedBy = "master", fetch = FetchType.EAGER)
	private Set<MasterDependent> masterDependents;

	public MasterDependent addMasterDependent(MasterDependent masterDependent) {
		getMasterDependents().add(masterDependent);
		masterDependent.setMaster(this);

		return masterDependent;
	}

	public MasterDependent removeMasterDependent(MasterDependent masterDependent) {
		getMasterDependents().remove(masterDependent);
		masterDependent.setMaster(null);

		return masterDependent;
	}

	@Override
	public String toString() {
		return "Master [masterId=" + masterId + ", masterAccountBalance=" + masterAccountBalance + ", masterEmail="
				+ masterEmail + ", masterGender=" + masterGender + ", masterMobile=" + masterMobile + ", masterName="
				+ masterName + ", materPassword=" + masterPassword + "]";
	}

	public Master(String masterId, int masterAccountBalance, String masterEmail, String masterGender,
			String masterMobile, String masterName, String materPassword) {
		super();
		this.masterId = masterId;
		this.masterAccountBalance = masterAccountBalance;
		this.masterEmail = masterEmail;
		this.masterGender = masterGender;
		this.masterMobile = masterMobile;
		this.masterName = masterName;
		this.masterPassword = materPassword;
	}

}