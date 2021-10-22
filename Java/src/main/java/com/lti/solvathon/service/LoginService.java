package com.lti.solvathon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.LoginDTO;
import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.Master;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.DependentRepo;
import com.lti.solvathon.repository.MasterRepo;

@Service
public class LoginService implements ILoginService {

	@Autowired
	MasterRepo masterRepo;
	@Autowired
	DependentRepo dependentRepo;

	@Override
	public LoginDTO loginUser(LoginDTO loginDTO) throws PowerHouseException {
//		BCryptPasswordEncoder bcryptPassword = new BCryptPasswordEncoder();
		String id = loginDTO.getId();
		String password = loginDTO.getPassword();
		String userType = loginDTO.getUser();
		LoginDTO dto = new LoginDTO();
		boolean matches = false;
		try {
			if (userType.equalsIgnoreCase("parent")) {
				Optional<Master> master = masterRepo.findById(id);
				System.out.println(master);
				if (master.isEmpty()) {
					throw new PowerHouseException("Invalid Parent ID");
				}
				Master master2 = master.get();

				// boolean matches = bcryptPassword.matches(password,
				// master2.getMasterPassword());
				if (master2.getMasterPassword().contentEquals(password)) {
					matches = true;
				}
				if (!matches) {
					throw new PowerHouseException("Invalid Master Password");
				}
				dto.setName(master2.getMasterName());
				return dto;
			}

			if (userType.equalsIgnoreCase("dependent")) {
				Optional<Dependent> dependent = dependentRepo.findById(id);
				System.out.println(dependent);
				if (dependent.isEmpty()) {
					throw new PowerHouseException("Invalid Dependent ID");
				}
				Dependent dependent2 = dependent.get();
				if (dependent2.getDependentPassword().contentEquals(password)) {
					matches = true;
				}
//				boolean matches = bcryptPassword.matches(password, dependent2.getDependentPassword());
				if (!matches) {
					throw new PowerHouseException("Invalid Dependent Password");
				}
				dto.setName(dependent2.getDependentName());
				return dto;
			}
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
		return null;
	}

}
