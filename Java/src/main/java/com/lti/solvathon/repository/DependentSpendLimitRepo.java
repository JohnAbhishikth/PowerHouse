package com.lti.solvathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lti.solvathon.entity.DependentSpendLimit;

public interface DependentSpendLimitRepo extends JpaRepository<DependentSpendLimit, String> {

}
