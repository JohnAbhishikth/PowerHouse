package com.lti.solvathon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.PendingStatusDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentSpendLimit;
import com.lti.solvathon.entity.Transaction;
import com.lti.solvathon.entity.TransactionRemark;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.DependentSpendLimitRepo;
import com.lti.solvathon.repository.MasterDependentRepo;
import com.lti.solvathon.repository.MasterDependentRepoCustom;
import com.lti.solvathon.repository.TransactionRemarkRepo;
import com.lti.solvathon.repository.TransactionRepo;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	DependentSpendLimitRepo dependentSpendLimitRepo;
	@Autowired
	TransactionRepo transactionRepo;
	@Autowired
	MasterDependentRepo masterDependentRepo;
	@Autowired
	MasterDependentRepoCustom masterDependentRepoCustom;
	@Autowired
	TransactionRemarkRepo transactionRemarkRepo;

	@Override
	public List<TransactionDTO> getAllTransactionsById(TransactionDTO dto) throws PowerHouseException {
		try {
			List<Transaction> findAllByCreditAccount = transactionRepo.findAllByCreditAccount(dto.getDependentId());
			List<TransactionDTO> transactionDTOList = new ArrayList<>();

			for (Transaction transaction : findAllByCreditAccount) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setCreditAccount(transaction.getCreditAccount());
				transactionDTO.setDebitAccount(transaction.getDebitAccount());
				transactionDTO.setStatus(transaction.getTransactionStatus());
				transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
				transactionDTO.setTransactionDate(transaction.getTransactionDate());
				transactionDTO.setTransactionId(transaction.getTransactionid());
				transactionDTO.setTransactionType(transaction.getTransactionType());
				transactionDTOList.add(transactionDTO);
			}
			List<Transaction> findAllByDebitAccount = transactionRepo.findAllByDebitAccount(dto.getDependentId());
			for (Transaction transaction : findAllByDebitAccount) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setCreditAccount(transaction.getCreditAccount());
				transactionDTO.setDebitAccount(transaction.getDebitAccount());
				transactionDTO.setStatus(transaction.getTransactionStatus());
				transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
				transactionDTO.setTransactionDate(transaction.getTransactionDate());
				transactionDTO.setTransactionId(transaction.getTransactionid());
				transactionDTO.setTransactionType(transaction.getTransactionType());
				transactionDTOList.add(transactionDTO);
			}
			return transactionDTOList;
		} catch (Exception e) {
			throw new PowerHouseException("Unable to fetch Transactions");
		}
	}

	@Override
	public PendingStatusDTO getAllPendingTransactions(PendingStatusDTO dto) {
		List<Dependent> dependentList = masterDependentRepoCustom.findDependentsByMaster(dto.getMasterId());
		if (dependentList.isEmpty()) {
			System.out.println("no dependents for :" + dto.getMasterId());
			return null;
		}
		PendingStatusDTO pendingStatusDTO = new PendingStatusDTO();
		pendingStatusDTO.setMasterId(dto.getMasterId());
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		for (Dependent dependent : dependentList) {
			List<Transaction> debitTransactionList = transactionRepo.findAllByDebitAccount(dependent.getDependentId());
			if (!debitTransactionList.isEmpty()) {
				for (Transaction transaction : debitTransactionList) {
					if (transaction.getTransactionStatus().equalsIgnoreCase("pending")) {
						TransactionDTO transactionDTO = new TransactionDTO();
						transactionDTO.setCreditAccount(transaction.getCreditAccount());
						transactionDTO.setDebitAccount(transaction.getDebitAccount());
						transactionDTO.setStatus(transaction.getTransactionStatus());
						transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
						transactionDTO.setTransactionDate(transaction.getTransactionDate());
						transactionDTO.setTransactionId(transaction.getTransactionid());
						transactionDTO.setTransactionType(transaction.getTransactionType());
						transactionDTOList.add(transactionDTO);
					}
				}

			}
		}
		pendingStatusDTO.setTransactionDTO(transactionDTOList);
		return pendingStatusDTO;
	}

	@Override
	public void updateTransactionStatus(TransactionDTO dto) throws PowerHouseException {
		try {
			Optional<Transaction> findById = transactionRepo.findById(dto.getTransactionId());
			if (findById.isEmpty()) {
				throw new PowerHouseException("Invalide Transaction Id");
			}
			Transaction transaction = findById.get();
			transaction.setTransactionStatus(dto.getStatus());
			TransactionRemark remark = new TransactionRemark(dto.getTransactionId(), dto.getRemark(), transaction);
			transactionRemarkRepo.save(remark);
			transactionRepo.save(transaction);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	@Override
	public void performTransaction(TransactionDTO dto) throws PowerHouseException {
		try {
			System.out.println(dto);
			Transaction transaction = new Transaction();
			transaction.setCreditAccount(dto.getCreditAccount());
			transaction.setDebitAccount(dto.getDebitAccount());
			transaction.setTransactionAmount(dto.getTransactionAmount());
			transaction.setTransactionDate(dto.getTransactionDate());
			transaction.setTransactionStatus(dto.getStatus());
			transaction.setTransactionType(dto.getTransactionType());

			transactionRepo.save(transaction);

			DependentSpendLimit dependentSpendLimit = dependentSpendLimitRepo.findById(dto.getDependentId()).get();
			int accountBalance = dependentSpendLimit.getAccountBalance();
			int finalAccBalance = accountBalance - dto.getTransactionAmount();
			dependentSpendLimit.setAccountBalance(finalAccBalance);
			dependentSpendLimitRepo.save(dependentSpendLimit);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

}
