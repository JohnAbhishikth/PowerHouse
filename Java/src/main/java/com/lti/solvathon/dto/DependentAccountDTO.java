package com.lti.solvathon.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DependentAccountDTO {

	DependentSpendLimitDTO dependentSpendLimitDTO;
	List<MerchantHotlistDTO> merchantHotlistDTO;
}
