package com.lti.solvathon.repository;

import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.Merchant;

public interface DependentMerchantHotlistCustomRepo {

	public DependentMerchantHotlist findAllMerchantHotlistForDependent(Dependent dependent, Merchant merchant,
			String merchantType);
}
