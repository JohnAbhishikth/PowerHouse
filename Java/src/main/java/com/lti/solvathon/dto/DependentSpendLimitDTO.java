package com.lti.solvathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DependentSpendLimitDTO {
	private String dependentId;
	private int accountBalance;
	private String spendFlag;
	private int spendLimit;
}
