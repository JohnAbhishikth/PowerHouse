package com.lti.solvathon.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.Master;
import com.lti.solvathon.entity.MasterDependent;

@Repository
public class MasterDependentRepoCustomImpl implements MasterDependentRepoCustom {

	@Autowired
	MasterDependentRepo masterDependentRepo;
	@Autowired
	MasterRepo masterRepo;

	@Override
	public void linkMasterAndDependent(String masterId, Dependent dependent) {

		Master master = masterRepo.findById(masterId).get();

		MasterDependent masterDepdent = new MasterDependent();
		masterDepdent.setDependentId(dependent.getDependentId());
		masterDepdent.setDependent(dependent);
		masterDepdent.setMaster(master);

		masterDependentRepo.save(masterDepdent);
	}

	@Override
	public List<Dependent> findDependentsByMaster(String masterId) {
		Master master = masterRepo.findById(masterId).get();

		Set<MasterDependent> masterDependents = master.getMasterDependents();
		List<MasterDependent> list = new ArrayList<MasterDependent>(masterDependents);
		List<Dependent> deptlist = new ArrayList<Dependent>();
		for (MasterDependent masterDependent : list) {
			Dependent dependent = masterDependent.getDependent();
			deptlist.add(dependent);
		}
		return deptlist;
	}

}
