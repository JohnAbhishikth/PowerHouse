package com.lti.solvathon.service;

import java.util.List;

import com.lti.solvathon.dto.PendingStatusDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.exception.PowerHouseException;

public interface ITransactionService {
	List<TransactionDTO> getAllTransactionsById(TransactionDTO dto) throws PowerHouseException;

	PendingStatusDTO getAllPendingTransactions(PendingStatusDTO dto);

	void updateTransactionStatus(TransactionDTO dto) throws PowerHouseException;

	void performTransaction(TransactionDTO dto) throws PowerHouseException;

	String checkTransaction(TransactionDTO dto) throws PowerHouseException;
}
