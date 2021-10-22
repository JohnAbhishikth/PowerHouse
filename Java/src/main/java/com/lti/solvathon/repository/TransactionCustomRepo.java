package com.lti.solvathon.repository;

import java.util.List;

import com.lti.solvathon.entity.Transaction;

public interface TransactionCustomRepo {

	List<Transaction> findByTransactionDate(String Date_afterThan,String Date_beforeThan, String dateFormat);
}
