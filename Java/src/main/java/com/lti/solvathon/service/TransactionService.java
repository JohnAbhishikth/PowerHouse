package com.lti.solvathon.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.solvathon.dto.PendingStatusDTO;
import com.lti.solvathon.dto.TransactionDTO;
import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.DependentSpendLimit;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.entity.Transaction;
import com.lti.solvathon.entity.TransactionRemark;
import com.lti.solvathon.exception.PowerHouseException;
import com.lti.solvathon.repository.DependentMerchantHotlistRepo;
import com.lti.solvathon.repository.DependentSpendLimitRepo;
import com.lti.solvathon.repository.MasterDependentRepo;
import com.lti.solvathon.repository.MasterDependentRepoCustom;
import com.lti.solvathon.repository.MerchantRepo;
import com.lti.solvathon.repository.TransactionRemarkRepo;
import com.lti.solvathon.repository.TransactionRepo;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	DependentSpendLimitRepo dependentSpendLimitRepo;
	@Autowired
	TransactionRepo transactionRepo;
	@Autowired
	MasterDependentRepo masterDependentRepo;
	@Autowired
	MasterDependentRepoCustom masterDependentRepoCustom;
	@Autowired
	TransactionRemarkRepo transactionRemarkRepo;
	@Autowired
	DependentMerchantHotlistRepo dependentMerchantHotlistRepo;
	@Autowired
	MerchantRepo merchantRepo;

	private String pending = "PENDING";
	private String success = "SUCCESS";

	@Override
	public List<TransactionDTO> getAllTransactionsById(TransactionDTO dto) throws PowerHouseException {
		try {
			List<Transaction> findAllByCreditAccount = transactionRepo.findAllByCreditAccount(dto.getDependentId());
			List<TransactionDTO> transactionDTOList = new ArrayList<>();

			for (Transaction transaction : findAllByCreditAccount) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setCreditAccount(transaction.getCreditAccount());
				transactionDTO.setDebitAccount(transaction.getDebitAccount());
				transactionDTO.setStatus(transaction.getTransactionStatus());
				transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
				transactionDTO.setTransactionDate(transaction.getTransactionDate());
				transactionDTO.setTransactionId(transaction.getTransactionid());
				transactionDTO.setTransactionType(transaction.getTransactionType());
				transactionDTOList.add(transactionDTO);
			}
			List<Transaction> findAllByDebitAccount = transactionRepo.findAllByDebitAccount(dto.getDependentId());
			for (Transaction transaction : findAllByDebitAccount) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setCreditAccount(transaction.getCreditAccount());
				transactionDTO.setDebitAccount(transaction.getDebitAccount());
				transactionDTO.setStatus(transaction.getTransactionStatus());
				transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
				transactionDTO.setTransactionDate(transaction.getTransactionDate());
				transactionDTO.setTransactionId(transaction.getTransactionid());
				transactionDTO.setTransactionType(transaction.getTransactionType());
				transactionDTOList.add(transactionDTO);
			}
			return transactionDTOList;
		} catch (Exception e) {
			throw new PowerHouseException("Unable to fetch Transactions");
		}
	}

	@Override
	public PendingStatusDTO getAllPendingTransactions(PendingStatusDTO dto) {
		List<Dependent> dependentList = masterDependentRepoCustom.findDependentsByMaster(dto.getMasterId());
		if (dependentList.isEmpty()) {
			System.out.println("no dependents for :" + dto.getMasterId());
			return null;
		}
		PendingStatusDTO pendingStatusDTO = new PendingStatusDTO();
		pendingStatusDTO.setMasterId(dto.getMasterId());
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		for (Dependent dependent : dependentList) {
			List<Transaction> debitTransactionList = transactionRepo.findAllByDebitAccount(dependent.getDependentId());
			if (!debitTransactionList.isEmpty()) {

				for (Transaction transaction : debitTransactionList) {
					if (transaction.getTransactionStatus().equalsIgnoreCase(pending)) {
						TransactionDTO transactionDTO = new TransactionDTO();
						transactionDTO.setCreditAccount(transaction.getCreditAccount());
						transactionDTO.setDebitAccount(transaction.getDebitAccount());
						transactionDTO.setStatus(transaction.getTransactionStatus());
						transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
						transactionDTO.setTransactionDate(transaction.getTransactionDate());
						transactionDTO.setTransactionId(transaction.getTransactionid());
						transactionDTO.setTransactionType(transaction.getTransactionType());
						transactionDTOList.add(transactionDTO);
					}
				}

			}
		}
		pendingStatusDTO.setTransactionDTO(transactionDTOList);
		return pendingStatusDTO;
	}

	@Override
	public void updateTransactionStatus(TransactionDTO dto) throws PowerHouseException {
		try {
			Optional<Transaction> findById = transactionRepo.findById(dto.getTransactionId());
			if (findById.isEmpty()) {
				throw new PowerHouseException("Invalide Transaction Id");
			}
			Transaction transaction = findById.get();
			transaction.setTransactionStatus(dto.getStatus());
			TransactionRemark remark = new TransactionRemark(dto.getTransactionId(), dto.getRemark(), transaction);
			transactionRemarkRepo.save(remark);
			transactionRepo.save(transaction);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	@Override
	public void performTransaction(TransactionDTO dto) throws PowerHouseException {
		try {
			System.out.println(dto);
			Transaction transaction = new Transaction();
			transaction.setCreditAccount(dto.getCreditAccount());
			transaction.setDebitAccount(dto.getDebitAccount());
			transaction.setTransactionAmount(dto.getTransactionAmount());
			transaction.setTransactionDate(dto.getTransactionDate());
			transaction.setTransactionStatus(dto.getStatus());
			transaction.setTransactionType(dto.getTransactionType());

			transactionRepo.save(transaction);

			Optional<DependentSpendLimit> findById = dependentSpendLimitRepo.findById(dto.getDependentId());
			if (dto.getStatus().equalsIgnoreCase("success") && findById.isPresent()) {
				DependentSpendLimit dependentSpendLimit = findById.get();
				int accountBalance = dependentSpendLimit.getAccountBalance();
				accountBalance -= dto.getTransactionAmount();
				dependentSpendLimit.setAccountBalance(accountBalance);
				dependentSpendLimitRepo.save(dependentSpendLimit);
			}
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	private Date getDate(String flag) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(System.currentTimeMillis());

		if (flag.equalsIgnoreCase("monthly flag")) {
			System.out.println("monthly");
			while (calendar.get(Calendar.DATE) > 1) {
				calendar.add(Calendar.DATE, -1); // Subtract 1 day until first day of month
			}
		} else if (flag.equalsIgnoreCase("weekly flag")) {
			System.out.println("weekly");
			while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
				calendar.add(Calendar.DATE, -1); // Subtract 1 day until first day of week
			}
		} else {
			System.out.println("daily flag");
			calendar.set(Calendar.MILLISECOND, 0);
		}

		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	private boolean isBalancePresent(DependentSpendLimit depSpendLimit, int transAmount) {
		int accountBalance = depSpendLimit.getAccountBalance();
		if (accountBalance < transAmount) {
			System.out.println("stop1");// checked
			return false;
		}
		System.out.println("stop2");// checked
		return true;
	}

	private boolean isPeriodicSpendLimit(DependentSpendLimit dependentSpendLimit, int transactionAmount) {
		String dependentId = dependentSpendLimit.getDependent_Id();
		String spendFlag = dependentSpendLimit.getSpendFlag();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDate = sdf.format(date);
		date = this.getDate(spendFlag);
		String fromDate = sdf.format(date);
		System.out.println("fromDate : " + fromDate);

		List<Transaction> transactionList = transactionRepo.findAllByTransactionByDebitAndDate(dependentId, fromDate,
				currentDate, success);
		int totalPeriodicSpentAmount = 0;

		if (!transactionList.isEmpty()) {
			for (Transaction transaction : transactionList) {
				totalPeriodicSpentAmount += transaction.getTransactionAmount();
			}
		}
		System.out.println("totalPeriodicSpentAmount : " + totalPeriodicSpentAmount);

		int depenSpendLimit = dependentSpendLimit.getSpendlimit();
		int balanceLeft = depenSpendLimit - totalPeriodicSpentAmount;

		if (balanceLeft <= 0 || balanceLeft < transactionAmount) {
			System.out.println("stop3");// checked
			return false;
		}
		System.out.println("stop4");// checked
		return true;
	}

	private Optional<DependentMerchantHotlist> getHotlistMerchant(String dependentId, String merchantName) {
		Optional<DependentMerchantHotlist> merchantHotlistByName = dependentMerchantHotlistRepo
				.findByDependentMerchantHotlist(dependentId, merchantName);
		if (merchantHotlistByName.isPresent())
			return merchantHotlistByName;

		String merchantType = "";
		Optional<Merchant> merchant = merchantRepo.findByMerchantName(merchantName);

		if (merchant.isPresent())
			merchantType = merchant.get().getMerchantType();
		return dependentMerchantHotlistRepo.findByDependentMerchantHotlist(dependentId, merchantType);

	}

	private boolean isAlert(DependentMerchantHotlist dependentMerchantHotlist) {
		String alertFlag = dependentMerchantHotlist.getAlertFLag();
		if (alertFlag.equalsIgnoreCase("no")) {
			System.out.println("stop5");// checked
			return false;
		}
		System.out.println("stop6");// checked
		return true;
	}

	private boolean isExceedsHotlistSpendLimit(DependentMerchantHotlist dependentMerchantHotlist,
			int transactionAmount) {
		int merchantSpendLimit = dependentMerchantHotlist.getSpendlimit();
		if (transactionAmount > merchantSpendLimit) {
			System.out.println("stop7");// checked
			return true;
		}
		System.out.println("stop8");// checked
		return false;
	}

	@Override
	public String checkTransaction(TransactionDTO dto) throws PowerHouseException {

//		1.Check Balance
//		2.Periodic Spend Limit
//		3.Merchant Hotlist
//		4.Alert
//		5.Merchant Spend Limit

//		dto.getDependentId() and dto.getDebitAccount() both are dependentIds only 
//		and both are same

		String dependentId = dto.getDebitAccount();
		Optional<DependentSpendLimit> findById = dependentSpendLimitRepo.findById(dependentId);
		if (findById.isEmpty()) {
			System.out.println("stop1");
			throw new PowerHouseException("Dependent SpendLimit Not Available");
		}
		DependentSpendLimit dependentSpendLimit = findById.get();
		System.out.println(dto);
		int transactionAmount = dto.getTransactionAmount();

		if (this.isBalancePresent(dependentSpendLimit, transactionAmount)) {
			// Credit Account means MerchantName
			String merchantName = dto.getCreditAccount();

			if (this.isPeriodicSpendLimit(dependentSpendLimit, transactionAmount)) {
				Optional<DependentMerchantHotlist> merchantHotlist = getHotlistMerchant(dependentId, merchantName);
				if (merchantHotlist.isPresent()) {
					if (isAlert(merchantHotlist.get())) {
						if (isExceedsHotlistSpendLimit(merchantHotlist.get(), transactionAmount))
							return pending;
						else
							return success;
					} else {
						throw new PowerHouseException("Transaction Rejected.It is a HotList Merchant with no alert");
					}
				} else {
					return success;
				}
			} else {
				throw new PowerHouseException("Transaction amount exceeding periodic spend limit");
			}
		} else {
			throw new PowerHouseException("Insufficient Account Balance");
		}
	}
}