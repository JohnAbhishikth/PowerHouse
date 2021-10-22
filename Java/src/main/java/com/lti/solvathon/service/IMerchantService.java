package com.lti.solvathon.service;

import java.util.List;

import com.lti.solvathon.dto.DependentMerchantDTO;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.exception.PowerHouseException;

public interface IMerchantService {

	List<String> getMerchantsOfType(DependentMerchantDTO dto);

	List<String> getAllDistinctMerchantTypes();

	List<Merchant> getAllMerchants();

	void createMerchant(Merchant merchantObj) throws PowerHouseException;

	List<Merchant> getAllMerchantsByName(String merchantName) throws PowerHouseException;
	
	void updateMerchantDetails(Merchant merchantObj) throws PowerHouseException;
	
}
