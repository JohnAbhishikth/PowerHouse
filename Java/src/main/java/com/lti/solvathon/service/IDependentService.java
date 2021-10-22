package com.lti.solvathon.service;

import java.util.List;

import com.lti.solvathon.dto.DependentDTO;
import com.lti.solvathon.dto.DependentSpendLimitDTO;
import com.lti.solvathon.dto.HotListDTO;
import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.dto.MasterDependentDTO;
import com.lti.solvathon.dto.MerchantHotlistDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.exception.PowerHouseException;

public interface IDependentService {

	void registerDependent(DependentDTO dependentDTO) throws PowerHouseException;

	void updateDependentDetails(DependentDTO dependentDTO) throws PowerHouseException;

	DependentDTO getDependentById(String id);

	void tagMasterAndDependent(MasterDependentDTO dto) throws PowerHouseException;

	void setSpendLimit(DependentSpendLimitDTO dto) throws PowerHouseException;

	void setHotList(HotListDTO dto) throws PowerHouseException;

	String performTransaction(TransactionDTO dto) throws PowerHouseException;

	List<DependentDTO> getAllDependents(MasterDTO dto);

	DependentSpendLimitDTO getSpendDetails(String id) throws PowerHouseException;

	List<MerchantHotlistDTO> getHotlistDetails(String id) throws PowerHouseException;

}
