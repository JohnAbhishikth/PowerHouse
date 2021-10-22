package com.lti.solvathon.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class LoginDTO {
	private String id;
	private String password;
	private String user;
	private String token;
	private String name;

}
