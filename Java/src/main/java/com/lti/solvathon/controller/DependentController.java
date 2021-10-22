package com.lti.solvathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lti.solvathon.dto.DependentAccountDTO;
import com.lti.solvathon.dto.DependentDTO;
import com.lti.solvathon.dto.DependentSpendLimitDTO;
import com.lti.solvathon.dto.HotListDTO;
import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.dto.MasterDependentDTO;
import com.lti.solvathon.dto.MerchantHotlistDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.dto.TransactionStatus;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.service.IDependentService;
import com.lti.solvathon.service.ITransactionService;

@Controller
@CrossOrigin
@RequestMapping("/dependent")
public class DependentController {

	@Autowired
	IDependentService dependentService;

	@Autowired
	ITransactionService transactionService;

	@PostMapping("/register")
	public ResponseEntity<?> registerDependent(@RequestBody DependentDTO dependentDTO) {
		try {
			dependentService.registerDependent(dependentDTO);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateDependentDetails(@RequestBody DependentDTO dependentDTO) {
		try {
			dependentService.updateDependentDetails(dependentDTO);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getdependentById(@PathVariable String id) {
		DependentDTO dependent = dependentService.getDependentById(id);
		return new ResponseEntity<>(dependent, HttpStatus.OK);
	}

	@PostMapping("/joinMaster")
	public ResponseEntity<?> tagMasterAndDependent(@RequestBody MasterDependentDTO dto) {
		try {
			dependentService.tagMasterAndDependent(dto);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/setSpendLimit")
	public ResponseEntity<?> setSpendLimit(@RequestBody DependentSpendLimitDTO dto) {
		try {
			dependentService.setSpendLimit(dto);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/setHotlist")
	public ResponseEntity<?> setHotList(@RequestBody HotListDTO dto) {
		try {
			dependentService.setHotList(dto);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/transaction")
	public ResponseEntity<?> performtransaction(@RequestBody TransactionDTO dto) {
		try {
			String transaction = dependentService.performTransaction(dto);
			dto.setDependentId(dto.getDebitAccount());
			dto.setStatus(transaction);
			transactionService.performTransaction(dto);
			TransactionStatus transactionStatus = new TransactionStatus();
			transactionStatus.setStatus(transaction);
			System.out.println("from controler : " + transactionStatus);
			return new ResponseEntity<>(transactionStatus, HttpStatus.OK);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
	}

	@PostMapping("/getAllDependents")
	public ResponseEntity<?> getAllDependents(@RequestBody MasterDTO dto) {
		List<DependentDTO> allDependents = dependentService.getAllDependents(dto);
		System.out.println(allDependents);
		return new ResponseEntity<>(allDependents, HttpStatus.OK);
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccountDetails(@PathVariable String id) {
		DependentSpendLimitDTO spendDetails = null;
		List<MerchantHotlistDTO> hotlistDetails = null;
		try {
			spendDetails = dependentService.getSpendDetails(id);
			hotlistDetails = dependentService.getHotlistDetails(id);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		DependentAccountDTO accountDTO = new DependentAccountDTO(spendDetails, hotlistDetails);
		return new ResponseEntity<>(accountDTO, HttpStatus.OK);
	}

}
