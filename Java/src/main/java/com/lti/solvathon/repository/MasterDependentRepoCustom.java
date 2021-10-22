package com.lti.solvathon.repository;

import java.util.List;

import com.lti.solvathon.entity.Dependent;

public interface MasterDependentRepoCustom {

	public void linkMasterAndDependent(String masterId, Dependent dependent);

	public List<Dependent> findDependentsByMaster(String masterId);

}
