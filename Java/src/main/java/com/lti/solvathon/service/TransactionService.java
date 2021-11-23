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
					if (transaction.getTransactionStatus().equalsIgnoreCase("PENDING")) {
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

			DependentSpendLimit dependentSpendLimit = dependentSpendLimitRepo.findById(dto.getDependentId()).get();
			int accountBalance = dependentSpendLimit.getAccountBalance();
			int finalAccBalance = accountBalance - dto.getTransactionAmount();
			dependentSpendLimit.setAccountBalance(finalAccBalance);
			dependentSpendLimitRepo.save(dependentSpendLimit);
		} catch (Exception e) {
			throw new PowerHouseException(e);
		}
	}

	@Override
	public String checkTransaction(TransactionDTO dto) throws PowerHouseException {
		System.out.println(dto);
		/*
		 * dto.getDependentId() and dto.getDebitAccount() both are dependentIds only and
		 * both are same
		 */
		String dependentId = dto.getDebitAccount();
		Optional<DependentSpendLimit> findById = dependentSpendLimitRepo.findById(dependentId);
		if (findById.isEmpty()) {
			System.out.println("stop1");
			throw new PowerHouseException("Dependent SpendLimit Not Available");
		}
		DependentSpendLimit dependentSpendLimit = findById.get();
		System.out.println(dependentSpendLimit);
		int accountBalance = dependentSpendLimit.getAccountBalance();
		int transactionAmount = dto.getTransactionAmount();
		if (accountBalance < transactionAmount) {
			System.out.println("stop2");// Checked
			throw new PowerHouseException("Insufficient Balance");
		}
		// Credit Account means MerchantName
		String creditAccount = dto.getCreditAccount();
		System.out.println("creditAccount : " + creditAccount);
		Optional<DependentMerchantHotlist> merchantHotlistByName = dependentMerchantHotlistRepo
				.findByDependentMerchantHotlist(dependentId, creditAccount);

		String merchantType = merchantRepo.findByMerchantName(creditAccount).get().getMerchantType();
		System.out.println("merchantType : " + merchantType);
		Optional<DependentMerchantHotlist> merchantHotlistByType = dependentMerchantHotlistRepo
				.findByDependentMerchantHotlist(dependentId, merchantType);

		String spendFlag = dependentSpendLimit.getSpendFlag();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDate = sdf.format(date);
		Date fromDate = this.getDate(spendFlag);
		String datefrom = sdf.format(fromDate);
		List<Transaction> transactionsByDebitAndDate = transactionRepo.findAllByTransactionByDebitAndDate(dependentId,
				datefrom, currentDate);
		List<Merchant> findByMerchantType = merchantRepo.findByMerchantType(merchantType);
		List<String> merchantName = new ArrayList<>();
		for (Merchant merchant : findByMerchantType) {
			merchantName.add(merchant.getMerchantName());
		}
		int totalSpentAmount = 0;
		int merchantTypeSpentAmount = 0;
		if (!transactionsByDebitAndDate.isEmpty()) {
			for (Transaction transaction : transactionsByDebitAndDate) {
				totalSpentAmount += transaction.getTransactionAmount();
				if (merchantName.contains(transaction.getCreditAccount())) {
					merchantTypeSpentAmount += transaction.getTransactionAmount();
				}

			}
		}

		int depenSpendLimit = dependentSpendLimit.getSpendlimit();
		int balanceLeft = depenSpendLimit - totalSpentAmount;

		final String EXCEEDING_LIMIT = "Transaction amount exceeding Spend Limit";

		if (merchantHotlistByName.isEmpty() && merchantHotlistByType.isEmpty()) {
			/* Not Hotlist Merchant transaction */
			if (balanceLeft <= 0 || balanceLeft < transactionAmount) {
				System.out.println("stop3");// Checked
				throw new PowerHouseException(EXCEEDING_LIMIT);
			} else {
				System.out.println("stop4");// Checked
				return "SUCCESS";
			}
		}

		if (!merchantHotlistByName.isEmpty()) {
			/* Hotlist Merchant transaction */

			String alertFlag = merchantHotlistByName.get().getAlertFLag();
			if (alertFlag.equalsIgnoreCase("no")) {
				System.out.println("stop5");// Checked
				throw new PowerHouseException("Transaction Rejected");
			} else {
				int merchantSpendLimit = merchantHotlistByName.get().getSpendlimit();
				if (merchantSpendLimit < transactionAmount || balanceLeft <= 0 || balanceLeft < transactionAmount) {
					System.out.println("stop6");// Checked
					throw new PowerHouseException(EXCEEDING_LIMIT);
				}
				System.out.println("stop7");// Checked
				return "PENDING";
			}
		}

		if (!merchantHotlistByName.isEmpty()) {

			String alertFlag = merchantHotlistByName.get().getAlertFLag();
			if (alertFlag.equalsIgnoreCase("no")) {
				System.out.println("stop8");
				throw new PowerHouseException("Transaction Rejected");
			} else {
				int merchantSpendLimit = merchantHotlistByName.get().getSpendlimit();
				int merchantBal = merchantSpendLimit - merchantTypeSpentAmount;
				if (merchantSpendLimit < transactionAmount || merchantBal <= 0 || merchantBal < transactionAmount) {
					System.out.println("stop9");
					throw new PowerHouseException(EXCEEDING_LIMIT);
				}
				System.out.println("stop10");
				return "PENDING";
			}
		}
		/**/
		if (!merchantHotlistByType.isEmpty()) {
			/* Hotlist Merchant transaction */

			String alertFlag = merchantHotlistByType.get().getAlertFLag();
			if (alertFlag.equalsIgnoreCase("no")) {
				System.out.println("stop11");// Checked
				throw new PowerHouseException("Transaction Rejected");
			} else {
				int merchantSpendLimit = merchantHotlistByType.get().getSpendlimit();
				if (merchantSpendLimit < transactionAmount || balanceLeft <= 0 || balanceLeft < transactionAmount) {
					System.out.println("stop12");// Checked
					throw new PowerHouseException(EXCEEDING_LIMIT);
				}
				System.out.println("stop13");// Checked
				return "PENDING";
			}
		}

		if (!merchantHotlistByType.isEmpty()) {

			String alertFlag = merchantHotlistByType.get().getAlertFLag();
			if (alertFlag.equalsIgnoreCase("no")) {
				System.out.println("stop14");
				throw new PowerHouseException("Transaction Rejected");
			} else {
				int merchantSpendLimit = merchantHotlistByType.get().getSpendlimit();
				int merchantBal = merchantSpendLimit - merchantTypeSpentAmount;
				if (merchantSpendLimit < transactionAmount || merchantBal <= 0 || merchantBal < transactionAmount) {
					System.out.println("stop15");
					throw new PowerHouseException(EXCEEDING_LIMIT);
				}
				System.out.println("stop16");
				return "PENDING";
			}
		}

		System.out.println("stop17");
		throw new PowerHouseException("Transaction Rejected");
	}

	private Date getDate(String flag) {
		if (flag.equalsIgnoreCase("daily")) {
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTime(new Date());
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR, 0);
			return calendar.getTime();
		}
		if (flag.equalsIgnoreCase("weekly")) {
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTimeInMillis(System.currentTimeMillis());
			while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
				calendar.add(Calendar.DATE, -1); // Subract 1 day until first day of week
			}
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			return calendar.getTime();

		}
		if (flag.equalsIgnoreCase("monthly")) {
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.setTimeInMillis(System.currentTimeMillis());
			while (calendar.get(Calendar.DATE) > 1) {
				calendar.add(Calendar.DATE, -1); // Subract 1 day until first day of month
			}
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			return calendar.getTime();

		}
		return null;
	}

}
