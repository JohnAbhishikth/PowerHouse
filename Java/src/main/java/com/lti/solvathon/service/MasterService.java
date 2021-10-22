package com.lti.solvathon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.entity.Master;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.MasterRepo;

@Service
public class MasterService implements IMasterService {

	@Autowired
	MasterRepo masterRepo;

	@Override
	public MasterDTO getMasterDetails(String id) throws PowerHouseException {
		Optional<Master> findById = masterRepo.findById(id);
		if (findById.isEmpty()) {
			throw new PowerHouseException("Invalid Master Id");
		}
		Master master = findById.get();
		MasterDTO dto = new MasterDTO();
		dto.setAccountBalance(master.getMasterAccountBalance());
		dto.setEmail(master.getMasterEmail());
		dto.setGender(master.getMasterGender());
		dto.setId(master.getMasterId());
		dto.setMobile(master.getMasterMobile());
		dto.setName(master.getMasterName());
		return dto;
	}

	@Override
	public void updateMasterDetails(MasterDTO masterDTO) throws PowerHouseException {
		Optional<Master> optionalMaster = masterRepo.findById(masterDTO.getId());

		if (optionalMaster.isEmpty()) {
			throw new PowerHouseException("Can't find Master");
		}

		Master master = optionalMaster.get();
		master.setMasterGender(masterDTO.getGender());
		master.setMasterEmail(masterDTO.getEmail());
		master.setMasterMobile(masterDTO.getMobile());
		master.setMasterName(masterDTO.getName());
		masterRepo.save(master);
	}

	@Override
	public void createMasterDetails(MasterDTO masterDTO) throws PowerHouseException {
		try {
			Optional<Master> findById = masterRepo.findById(masterDTO.getId());
			if (findById.isPresent())
				throw new PowerHouseException("Parent Id already Present");

			Master master = new Master(masterDTO.getId(), 0, masterDTO.getEmail(), masterDTO.getGender(),
					masterDTO.getMobile(), masterDTO.getName(), masterDTO.getPassword());
			masterRepo.save(master);
		} catch (Exception e) {
			throw e;
		}
	}

}
