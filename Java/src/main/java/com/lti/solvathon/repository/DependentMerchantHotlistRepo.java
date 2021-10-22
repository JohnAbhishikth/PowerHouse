package com.lti.solvathon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.DependentMerchantHotlistPK;


public interface DependentMerchantHotlistRepo 
	   extends JpaRepository<DependentMerchantHotlist, DependentMerchantHotlistPK> {


	@Query(value = "Select * from dependent_merchant_hotlist where Dependent_Id=?1", nativeQuery = true)
	List<DependentMerchantHotlist> findAllByDependentId(String dependentId);

	@Query(value = "Select * from dependent_merchant_hotlist where merchant=?1", nativeQuery = true)
	List<DependentMerchantHotlist> findAllByMerchant(String merchant);

	@Query(value = "Select * from dependent_merchant_hotlist where merchant like ?1", nativeQuery = true)
	List<DependentMerchantHotlist> findByMerchantContaining(String $merchant$);
	
	@Query(value = "Select * from dependent_merchant_hotlist where dependent_Id=?1 and merchant=?2", nativeQuery = true)
	Optional<DependentMerchantHotlist> findByDependentMerchantHotlist(String dependentId, String merchant);

}
