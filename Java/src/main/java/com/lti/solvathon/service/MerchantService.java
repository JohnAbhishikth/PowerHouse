package com.lti.solvathon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.DependentMerchantDTO;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.MerchantRepo;

@Service
public class MerchantService implements IMerchantService {

	@Autowired
	MerchantRepo merchantRepo;

	@Override
	public void createMerchant(Merchant merchantObj) throws PowerHouseException {

		Optional<Merchant> merchantId = merchantRepo.findById(merchantObj.getMerchantId().toUpperCase());
		this.checkIfPresent(merchantId, "MerchantId");

		Optional<Merchant> merchantName = merchantRepo.findByMerchantName(merchantObj.getMerchantName().toUpperCase());
		this.checkIfPresent(merchantName, "MerchantName");
		try {
			Merchant merchant = new Merchant(merchantObj.getMerchantId().toUpperCase(),
					merchantObj.getMerchantName().toUpperCase(), merchantObj.getMerchantType());
			merchantRepo.save(merchant);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	@Override
	public List<Merchant> getAllMerchants() {
		return merchantRepo.findAll();
	}

	private void checkIfPresent(Optional<?> object, String objType) throws PowerHouseException {
		if (object.isPresent()) {
			System.out.println(objType + "already Exist");
			throw new PowerHouseException(objType + " already Exist");
		}
	}

	@Override
	public List<String> getAllDistinctMerchantTypes() {
		return merchantRepo.findDistinctMerchantType();
	}

	@Override
	public List<String> getMerchantsOfType(DependentMerchantDTO dto) {
		return merchantRepo.findallMerchantOfType(dto.getMerchantType());
	}

	@Override
	public List<Merchant> getAllMerchantsByName(String merchantName) throws PowerHouseException {
		try {
			return merchantRepo.findByMerchantNameContaining(merchantName.toUpperCase());
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateMerchantDetails(Merchant merchantObj) throws PowerHouseException {
		try {
			Optional<Merchant> findById = merchantRepo.findById(merchantObj.getMerchantId());
			if (findById.isEmpty())
				throw new PowerHouseException("Not a valid Merchant");
			Merchant merchant = findById.get();
			merchant.setMerchantName(merchantObj.getMerchantName().toUpperCase());
			merchantRepo.save(merchant);
		} catch (Exception e) {
			throw e;
		}

	}
}
