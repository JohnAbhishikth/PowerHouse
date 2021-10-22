package com.lti.solvathon.service;

import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.exception.PowerHouseException;

public interface IMasterService {

	MasterDTO getMasterDetails(String id) throws PowerHouseException;

	void updateMasterDetails(MasterDTO masterDTO) throws PowerHouseException;

	void createMasterDetails(MasterDTO masterDTO) throws PowerHouseException;
}
