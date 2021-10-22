package com.lti.solvathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lti.solvathon.entity.Dependent;

public interface DependentRepo extends JpaRepository<Dependent, String> {

}
