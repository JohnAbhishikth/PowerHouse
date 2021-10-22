package com.lti.solvathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.solvathon.entity.MasterDependent;

public interface MasterDependentRepo extends JpaRepository<MasterDependent, String> {

	@Query(value = "select * from master_dependent where Master_Id=?1", nativeQuery = true)
	List<MasterDependent> findAllByMasterId(String id);

}
