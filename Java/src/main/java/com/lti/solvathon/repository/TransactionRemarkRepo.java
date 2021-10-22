package com.lti.solvathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lti.solvathon.entity.TransactionRemark;

public interface TransactionRemarkRepo extends JpaRepository<TransactionRemark, Integer> {

}
