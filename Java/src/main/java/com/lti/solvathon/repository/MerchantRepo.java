package com.lti.solvathon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.solvathon.entity.Merchant;

public interface MerchantRepo extends JpaRepository<Merchant, String> {

	List<Merchant> findByMerchantNameContaining(String merchantName);

	Optional<Merchant> findByMerchantName(String merchantName);

	@Query(value = " Select * from merchant where Merchant_Type=?1", nativeQuery = true)
	List<Merchant> findByMerchantType(String merchantType);

	@Query(value = "Select distinct(merchant_type) from merchant", nativeQuery = true)
	List<String> findDistinctMerchantType();

	@Query(value = "Select Merchant_Name from merchant where Merchant_Type=?1", nativeQuery = true)
	List<String> findallMerchantOfType(String merchantType);

}
