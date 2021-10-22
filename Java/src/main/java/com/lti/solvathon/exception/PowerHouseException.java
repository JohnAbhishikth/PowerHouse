package com.lti.solvathon.exception;

public class PowerHouseException extends Exception {

	private static final long serialVersionUID = -5994554209590151630L;

	public PowerHouseException(String msg) {
		super(msg);
	}

	public PowerHouseException(Throwable e) {
		super(e);
	}
}
