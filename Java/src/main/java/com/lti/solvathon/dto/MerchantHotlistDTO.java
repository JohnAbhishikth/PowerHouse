package com.lti.solvathon.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MerchantHotlistDTO {
	private String merchant;
	private boolean alertFlag;
	private int spendLimit;
	private String merchantFlag;

	public boolean getAlertFlag() {
		return this.alertFlag;
	}

}
