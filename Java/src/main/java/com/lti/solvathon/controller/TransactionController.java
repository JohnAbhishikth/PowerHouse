package com.lti.solvathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lti.solvathon.dto.PendingStatusDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.service.ITransactionService;

@Controller
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	ITransactionService transactionService;

	@PostMapping("/getByDepId")
	public ResponseEntity<?> getAllTransactionsByDepId(@RequestBody TransactionDTO dto) {
		try {
			List<TransactionDTO> allTransactions = transactionService.getAllTransactionsById(dto);
			return new ResponseEntity<>(allTransactions, HttpStatus.OK);
		} catch (PowerHouseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/pending")
	public ResponseEntity<?> getPendingTransactions(@RequestBody PendingStatusDTO dto) {
		PendingStatusDTO allPendingTransaction = transactionService.getAllPendingTransactions(dto);
		return new ResponseEntity<>(allPendingTransaction, HttpStatus.OK);
	}

	@PostMapping("/updateStatus")
	public ResponseEntity<?> updateTransactionStatus(@RequestBody TransactionDTO dto) {
		try {
			transactionService.updateTransactionStatus(dto);
		} catch (PowerHouseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
