package com.lti.solvathon.service;

import com.lti.solvathon.dto.LoginDTO;
import com.lti.solvathon.exception.PowerHouseException;

public interface ILoginService {

	LoginDTO loginUser(LoginDTO loginDTO) throws PowerHouseException;


}
