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

import com.lti.solvathon.dto.DependentMerchantDTO;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.service.IMerchantService;

@Controller
@CrossOrigin
@RequestMapping("/merchant/")
public class MerchantController {

	@Autowired
	IMerchantService merchantService;

	@PostMapping("create")
	public ResponseEntity<?> createMerchant(@RequestBody Merchant merchant) {
		try {
			merchantService.createMerchant(merchant);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
	}

	@GetMapping("getAll")
	public ResponseEntity<?> getAllMerchant() {
		List<Merchant> allMerchant = merchantService.getAllMerchants();
		return new ResponseEntity<>(allMerchant, HttpStatus.OK);

	}

	@GetMapping("getAllMerchantType")
	public ResponseEntity<?> getAllMerchantType() {
		List<String> allMerchantType = merchantService.getAllDistinctMerchantTypes();
		if (allMerchantType == null || allMerchantType.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(allMerchantType, HttpStatus.OK);

	}

	@PostMapping("getAllMerchantsofType")
	public ResponseEntity<?> getAllMerchantsofType(@RequestBody DependentMerchantDTO dto) {
		List<String> merchantOfType = merchantService.getMerchantsOfType(dto);
		if (merchantOfType == null || merchantOfType.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(merchantOfType, HttpStatus.OK);
	}

	@GetMapping("{merchantName}")
	public ResponseEntity<?> getAllMerchantsByName(@PathVariable String merchantName) {
		try {
			List<Merchant> allMerchantsByName = merchantService.getAllMerchantsByName(merchantName);
			return ResponseEntity.ok(allMerchantsByName);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("update")
	public ResponseEntity<?> updateMerchantDetails(@RequestBody Merchant merchant) {
		try {
			merchantService.updateMerchantDetails(merchant);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
