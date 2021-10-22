package com.lti.solvathon.controller;

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

import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.service.IMasterService;

@Controller
@CrossOrigin
@RequestMapping("/master")
public class MasterController {

	@Autowired
	IMasterService masterService;

	@GetMapping("/id/{id}")
	public ResponseEntity<?> getMasterDetails(@PathVariable String id) {

		MasterDTO masterDetails = null;
		try {
			masterDetails = masterService.getMasterDetails(id);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(masterDetails, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateMasterDetails(@RequestBody MasterDTO masterDTO) {
		try {
			masterService.updateMasterDetails(masterDTO);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createMaster(@RequestBody MasterDTO masterDTO) {
		try {
			masterService.createMasterDetails(masterDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.ok(e);
		}
	}

}
