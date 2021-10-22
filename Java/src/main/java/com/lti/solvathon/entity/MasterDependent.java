package com.lti.solvathon.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the master_dependent database table.
 * 
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "master_dependent")
@NamedQuery(name = "MasterDependent.findAll", query = "SELECT m FROM MasterDependent m")
public class MasterDependent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String dependentId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Dependent_Id")
	private Dependent dependent;

	// bi-directional many-to-one association to Master
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Master_Id")
	private Master master;

}