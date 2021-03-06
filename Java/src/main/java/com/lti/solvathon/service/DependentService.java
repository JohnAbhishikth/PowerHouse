package com.lti.solvathon.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.DependentDTO;
import com.lti.solvathon.dto.DependentSpendLimitDTO;
import com.lti.solvathon.dto.HotListDTO;
import com.lti.solvathon.dto.MasterDTO;
import com.lti.solvathon.dto.MasterDependentDTO;
import com.lti.solvathon.dto.MerchantHotlistDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.DependentMerchantHotlistPK;
import com.lti.solvathon.entity.DependentSpendLimit;
import com.lti.solvathon.entity.MasterDependent;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.entity.Transaction;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.DependentMerchantHotlistRepo;
import com.lti.solvathon.repository.DependentRepo;
import com.lti.solvathon.repository.DependentSpendLimitRepo;
import com.lti.solvathon.repository.MasterDependentRepo;
import com.lti.solvathon.repository.MasterDependentRepoCustom;
import com.lti.solvathon.repository.MerchantRepo;
import com.lti.solvathon.repository.TransactionRepo;

@Service
public class DependentService implements IDependentService {

	@Autowired
	MerchantRepo merchantRepo;
	@Autowired
	DependentRepo dependentRepo;
	@Autowired
	TransactionRepo transactionRepo;
	@Autowired
	MasterDependentRepo masterDependentRepo;
	@Autowired
	MasterDependentRepoCustom masterDependentRepoCustom;
	@Autowired
	DependentSpendLimitRepo dependentSpendLimitRepo;
	@Autowired
	DependentMerchantHotlistRepo dependentMerchantHotlistRepo;

	@Override
	public void registerDependent(DependentDTO dependentDTO) throws PowerHouseException {
		Optional<Dependent> optional = dependentRepo.findById(dependentDTO.getId());
		if (optional.isPresent()) {
			throw new PowerHouseException("DependentId Available");
		}
		Dependent dependent = new Dependent(dependentDTO.getId(), dependentDTO.getAge(), dependentDTO.getMail(),
				dependentDTO.getGender(), dependentDTO.getMobile(), dependentDTO.getName(), "password");
		dependentRepo.save(dependent);
	}

	@Override
	public void updateDependentDetails(DependentDTO dependentDTO) throws PowerHouseException {
		Optional<Dependent> optionalDependent = dependentRepo.findById(dependentDTO.getId());
		if (optionalDependent.isEmpty()) {
			throw new PowerHouseException("DependentId Not Available");
		}
		Dependent dependent = optionalDependent.get();
		dependent.setDependentId(dependentDTO.getId());
		dependent.setDependentGender(dependentDTO.getGender());
		dependent.setDependentEmail(dependentDTO.getMail());
		dependent.setDependentMobile(dependentDTO.getMobile());
		dependent.setDependentName(dependentDTO.getName());
		dependentRepo.save(dependent);
	}

	@Override
	public DependentDTO getDependentById(String id) {
		Optional<Dependent> dependent = dependentRepo.findById(id);
		if (dependent.isPresent()) {
			DependentDTO dto = new DependentDTO();
			Dependent dep = dependent.get();
			dto.setName(dep.getDependentName());
			dto.setAge(dep.getDependentAge());
			dto.setMail(dep.getDependentEmail());
			dto.setGender(dep.getDependentGender());
			dto.setId(dep.getDependentId());
			dto.setMobile(dep.getDependentMobile());
//			dto.setPassword(dep.getDependentPassword());
			return dto;
		}
		return null;
	}

	@Override
	public void tagMasterAndDependent(MasterDependentDTO dto) throws PowerHouseException {
		Optional<Dependent> dependent = dependentRepo.findById(dto.getDependentId());
		if (dependent.isEmpty()) {
			throw new PowerHouseException("Dependent not Available");
		}
		masterDependentRepoCustom.linkMasterAndDependent(dto.getMasterId(), dependent.get());
	}

	@Override
	public void setSpendLimit(DependentSpendLimitDTO dto) throws PowerHouseException {
		try {
			Optional<Dependent> dependent = dependentRepo.findById(dto.getDependentId());
			if (dependent.isEmpty()) {
				throw new PowerHouseException("Dependent Not Found");
			}
			DependentSpendLimit dependentSpendLimit = new DependentSpendLimit();
			dependentSpendLimit.setAccountBalance(10000);
			Dependent dep = dependent.get();
			dependentSpendLimit.setDependent(dep);
			dependentSpendLimit.setDependent_Id(dep.getDependentId());
			dependentSpendLimit.setSpendFlag(dto.getSpendFlag());
			dependentSpendLimit.setSpendlimit(dto.getSpendLimit());

			dependentSpendLimitRepo.save(dependentSpendLimit);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}

	}

	@Override
	public void setHotList(HotListDTO dto) throws PowerHouseException {
		try {
			String dependentId = dto.getDependentId();
			Optional<Dependent> findById = dependentRepo.findById(dependentId);
			if (findById.isEmpty()) {
				throw new PowerHouseException("Dependent Id Not Available");
			}
			Dependent dependent = findById.get();
			DependentMerchantHotlist dependentMerchantHotlist;
			List<MerchantHotlistDTO> hotList = dto.getHotlist();

			for (MerchantHotlistDTO hotlistDTO : hotList) {
				System.out.println(hotlistDTO);
				DependentMerchantHotlistPK id = new DependentMerchantHotlistPK();
				id.setDependent_Id(dependent.getDependentId());
				id.setMerchant(hotlistDTO.getMerchant());
				boolean flag = hotlistDTO.getAlertFlag();

				String alertFlag = "";

				if (flag) {
					alertFlag = "yes";
				} else {
					alertFlag = "no";
				}

				String merchantFlag = hotlistDTO.getMerchantFlag();
				int spendLimit = hotlistDTO.getSpendLimit();

				dependentMerchantHotlist = new DependentMerchantHotlist(id, alertFlag, merchantFlag, spendLimit,
						dependent);
				dependentMerchantHotlistRepo.save(dependentMerchantHotlist);
			}
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	@Override
	public List<DependentDTO> getAllDependents(MasterDTO dto) {
		List<MasterDependent> findAllByMasterId = masterDependentRepo.findAllByMasterId(dto.getId());
		List<DependentDTO> dependentDTOList = new ArrayList<>();
		if (findAllByMasterId.size() >= 1) {
			for (MasterDependent dependent : findAllByMasterId) {
				String dependentId = dependent.getDependentId();
				Dependent dependent2 = dependentRepo.findById(dependentId).get();
				DependentDTO dependentDTO = new DependentDTO(dependent2.getDependentId(), dependent2.getDependentAge(),
						dependent2.getDependentEmail(), dependent2.getDependentGender(),
						dependent2.getDependentMobile(), dependent2.getDependentName(), "");
				dependentDTOList.add(dependentDTO);
			}
		}
		return dependentDTOList;
	}

	@Override
	public DependentSpendLimitDTO getSpendDetails(String id) throws PowerHouseException {

		Optional<DependentSpendLimit> findById = dependentSpendLimitRepo.findById(id);
		if (findById.isEmpty()) {
			throw new PowerHouseException("Dependent Not Available");
		}
		DependentSpendLimit dependentSpendLimit = findById.get();
		DependentSpendLimitDTO dto = new DependentSpendLimitDTO();
		dto.setAccountBalance(dependentSpendLimit.getAccountBalance());
		dto.setDependentId(dependentSpendLimit.getDependent_Id());
		dto.setSpendFlag(dependentSpendLimit.getSpendFlag());
		dto.setSpendLimit(dependentSpendLimit.getSpendlimit());

		return dto;
	}

	@Override
	public List<MerchantHotlistDTO> getHotlistDetails(String id) throws PowerHouseException {
		List<DependentMerchantHotlist> findAllByDependentId = dependentMerchantHotlistRepo.findAllByDependentId(id);
		if (findAllByDependentId.isEmpty()) {
			throw new PowerHouseException("Dependent Not Available");
		}
		List<MerchantHotlistDTO> dto = new ArrayList<>();
		findAllByDependentId.forEach(value -> {
			MerchantHotlistDTO merchantHotlistDTO = new MerchantHotlistDTO();
			merchantHotlistDTO.setMerchant(value.getId().getMerchant());
			merchantHotlistDTO.setMerchantFlag(value.getMerchantflag());
			merchantHotlistDTO.setSpendLimit(value.getSpendlimit());
			String alertFLag = value.getAlertFLag();
			merchantHotlistDTO.setAlertFlag(alertFLag.equalsIgnoreCase("yes") ? true : false);

			dto.add(merchantHotlistDTO);
		});
		return dto;
	}
}
