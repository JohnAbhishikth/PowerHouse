package com.lti.solvathon.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class HotListDTO {
	private String dependentId;

	private List<MerchantHotlistDTO> hotlist;
}
