package com.lti.solvathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.solvathon.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

	List<Transaction> findAllByCreditAccount(String creditAccount);

	List<Transaction> findAllByDebitAccount(String debitAccount);

	List<Transaction> findAllByTransactionStatus(String status);

	List<Transaction> findAllByTransactionType(String Type);

	@Query(value = "Select * from transactions where transaction_date>=?1", nativeQuery = true)
	List<Transaction> findAllByTransactionDateAfter(String date);

	@Query(value = "Select * from transactions where transaction_date<=?1", nativeQuery = true)
	List<Transaction> findAllByTransactionDateBefore(String date);

	@Query(value = "Select * from transactions where transaction_date>=?1 and transaction_date<=?2", nativeQuery = true)
	List<Transaction> findAllByTransactionDateInRange(String date_AfterThan, String date_BeforeThan);

	@Query(value = "Select * from transactions where Debit_Account=?1 and transaction_date>=?2 and transaction_date<=?3", nativeQuery = true)
	List<Transaction> findAllByTransactionByDebitAndDate(String debitAccount, String date, String currentDate);
}
