package com.lti.solvathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lti.solvathon.dto.LoginDTO;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.service.ILoginService;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	ILoginService loginService;

//	@Autowired
//	JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
		LoginDTO dto = new LoginDTO();
		try {
			LoginDTO login = loginService.loginUser(loginDTO);
			if (login == null) {
				return ResponseEntity.ok("Cant Login");
			}
			String generateToken = "";/* jwtTokenUtil.generateToken(loginDTO); */
			dto.setToken(generateToken);
			dto.setName(login.getName());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (PowerHouseException e) {
			return ResponseEntity.ok(e);
		}

	}

	@PostMapping("/admin")
	public ResponseEntity<?> adminLogin(@RequestBody LoginDTO loginDTO) {
		try {
			if (loginDTO.getId().contentEquals("admin") && loginDTO.getPassword().contentEquals("admin"))
				return new ResponseEntity<>(HttpStatus.OK);
			else
				throw new PowerHouseException("Invalid Credentials");
		} catch (Exception e) {
			System.out.println("Invalid Credentials");
			return ResponseEntity.ok(e);
		}

	}
}
