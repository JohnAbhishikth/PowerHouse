package com.lti.solvathon.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.Merchant;

@Repository
public class DependentMerchantHotlistCustomRepoImpl implements DependentMerchantHotlistCustomRepo {

	@Autowired
	DependentMerchantHotlistRepo dependentMerchantHotlistRepo;

	/**
	 * Can be used during Transaction to find merchant spent limits
	 */
	@Override
	public DependentMerchantHotlist findAllMerchantHotlistForDependent(Dependent dependent, Merchant merchant,
			String merchantType) {
		DependentMerchantHotlist dependentMerchantHotlist = null;
		String dependentId = dependent.getDependentId();
		String merchantName = merchant.getMerchantName();

		Optional<DependentMerchantHotlist> findByDependentMerchantHotlist = dependentMerchantHotlistRepo
				.findByDependentMerchantHotlist(dependentId, merchantName);

		if (findByDependentMerchantHotlist.isPresent()) {
			dependentMerchantHotlist = findByDependentMerchantHotlist.get();
			System.out.println("Got by Merchant Name");
			return dependentMerchantHotlist;
		} else {
			findByDependentMerchantHotlist = dependentMerchantHotlistRepo.findByDependentMerchantHotlist(dependentId,
					merchantType);
			if (findByDependentMerchantHotlist.isPresent()) {
				dependentMerchantHotlist = findByDependentMerchantHotlist.get();
				System.out.println("Got by Merchant Type");
				return dependentMerchantHotlist;
			}

		}
		return null;
	}
}
