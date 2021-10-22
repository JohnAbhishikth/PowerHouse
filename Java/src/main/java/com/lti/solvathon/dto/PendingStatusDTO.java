package com.lti.solvathon.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PendingStatusDTO {
	private String masterId;
	private List<TransactionDTO> transactionDTO;

}
