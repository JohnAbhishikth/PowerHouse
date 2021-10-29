package com.lti.solvathon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lti.solvathon.entity.Dependent;
import com.lti.solvathon.entity.DependentMerchantHotlist;
import com.lti.solvathon.entity.DependentMerchantHotlistPK;
import com.lti.solvathon.entity.DependentSpendLimit;
import com.lti.solvathon.entity.Master;
import com.lti.solvathon.entity.MasterDependent;
import com.lti.solvathon.entity.Merchant;
import com.lti.solvathon.entity.Transaction;
import com.lti.solvathon.entity.TransactionRemark;
import com.lti.solvathon.repository.DependentMerchantHotlistCustomRepo;
import com.lti.solvathon.repository.DependentMerchantHotlistRepo;
import com.lti.solvathon.repository.DependentRepo;
import com.lti.solvathon.repository.DependentSpendLimitRepo;
import com.lti.solvathon.repository.MasterDependentRepo;
import com.lti.solvathon.repository.MasterDependentRepoCustom;
import com.lti.solvathon.repository.MasterRepo;
import com.lti.solvathon.repository.MerchantRepo;
import com.lti.solvathon.repository.TransactionCustomRepo;
import com.lti.solvathon.repository.TransactionRemarkRepo;
import com.lti.solvathon.repository.TransactionRepo;

@SpringBootTest
class PowerHouseApplicationTests {
	@Autowired
	DependentMerchantHotlistRepo dependentMerchantHotlistRepo;
	@Autowired
	DependentMerchantHotlistCustomRepo dependentMerchantHotlistCustomRepo;
	@Autowired
	DependentRepo dependentRepo;
	@Autowired
	DependentSpendLimitRepo dependentSpendLimitRepo;
	@Autowired
	MasterDependentRepo masterDependentRepo;
	@Autowired
	MasterDependentRepoCustom masterDependentRepoCustom;
	@Autowired
	MasterRepo masterRepo;
	@Autowired
	MerchantRepo merchantRepo;
	@Autowired
	TransactionRemarkRepo transactionRemarkRepo;
	@Autowired
	TransactionCustomRepo transactionCustomRepo;
	@Autowired
	TransactionRepo transactionRepo;

//	private static BCryptPasswordEncoder bcryptPasswordEncoder;
//
//	static {
//		bcryptPasswordEncoder = new BCryptPasswordEncoder();
//	}
//
	@Test
	public void masterInsert() {

		Master master = new Master();
		master.setMasterAccountBalance(10000);
		master.setMasterEmail("email@gmail.com");
		master.setMasterGender("M");
		master.setMasterId("Master1");
		master.setMasterMobile("7894561230");
		master.setMasterName("Master Name");
		master.setMasterPassword("password");
		masterRepo.save(master);

	}

	// working
	@Test
	public void masterSelectById() {
		Master master = masterRepo.findById("m1").get();
		System.out.println(master);
	}

	// working
	@Test
	public void masterSelectAll() {
		List<Master> findAll = masterRepo.findAll();
		System.out.println(findAll);
	}

	// working
	@Test
	public void masterUpdate() {
		Master master = masterRepo.findById("m1").get();
		master.setMasterAccountBalance(0);
		masterRepo.save(master);
	}

	// working
	@Test
	void deleteMaster() {
		masterRepo.deleteById("m1");
	}

	// Notworking
	@Test
	public void masterCheckPassword() {
		Master master = masterRepo.findById("m2").get();
		assertEquals(master.getMasterPassword(), "password");
//		assertTrue(bcryptPasswordEncoder.matches("password", master.getMasterPassword()));
	}

	// working
	@Test
	public void masterDelete() {
		masterRepo.deleteById("m1");
	}

	// =========================================================================================================

	// working
	@Test
	public void dependentInsert() {
		Dependent dependent = new Dependent("D1", 13, "d2@gmail.com", "M", "9872345678", "Amir", "Amir123");
		dependentRepo.save(dependent);
	}

	// working
	@Test
	public void dependentSelectById() {
		Dependent dependent = dependentRepo.findById("d1").get();
		System.out.println(dependent);
	}

	// working
	@Test
	public void dependentSelectAll() {
		List<Dependent> findAll = dependentRepo.findAll();
		System.out.println(findAll);
	}

	// Notworking
	@Test
	public void dependentUpdate() {
		Dependent dependent = dependentRepo.findById("d2").get();
//		dependent.setDependentPassword(bcryptPasswordEncoder.encode("pass"));
		dependentRepo.save(dependent);
	}

	// working
	@Test
	public void dependentDelete() {
		dependentRepo.deleteById("d1");
	}

	// =========================================================================================================

	// working
	@Test
	public void masterDependentInsert() {
		Dependent dependent = new Dependent("d1", 30, "@email.com", "t", "0000", "Name", "Pass");
//			Dependent dependent = dependentRepo.findById("d2").get();
		masterDependentRepoCustom.linkMasterAndDependent("Master1", dependent);
	}

	// working
	@Test
	public void getMasterByDependentId() {
		Optional<MasterDependent> findById = masterDependentRepo.findById("d3");
		MasterDependent masterDependent = findById.get();
		Master master = masterDependent.getMaster();
		System.out.println(master);

	}

	// working
	@Test
	public void getDependentsByMasterId() {
		List<Dependent> findDependentsByMaster = masterDependentRepoCustom.findDependentsByMaster("m2");
		System.out.println(findDependentsByMaster);
	}

	// working
	@Test
	public void getDependentsAllByIds() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("d2");
		arrayList.add("d3");
		System.out.println(dependentRepo.findAllById(arrayList));
	}

	// =========================================================================================================

	// working
	@Test
	public void MerchantInsert() {
		Merchant merchant = new Merchant("D1", "DMart", "Groceries");
		merchantRepo.save(merchant);
	}

//		working
	@Test
	public void MerchantSelect() {
		Optional<Merchant> findById = merchantRepo.findById("m1");
		if (findById.isPresent())
			System.out.println(findById);
		else
			System.out.println("Empty Merchant");
	}

	// working
	@Test
	public void MerchantSelectAll() {
		System.out.println(merchantRepo.findAll());

	}

	// working
	@Test
	public void MerchantUpdate() {
		Merchant merchant = merchantRepo.findById("m3").get();
		merchant.setMerchantName("nameChange");
		merchantRepo.save(merchant);

	}

	// working
	@Test
	public void MerchantDelete() {
		merchantRepo.deleteById("m3");

	}

	// working
	@Test
	public void findByMerchantName() {
		List<Merchant> findByMerchantName = merchantRepo.findByMerchantNameContaining("abc");
		System.out.println(findByMerchantName);
	}

	// working
	@Test
	public void findByMerchantType() {
		List<Merchant> findByMerchantTypeContaining = merchantRepo.findByMerchantType("pety");
		System.out.println(findByMerchantTypeContaining);
	}

	@Test
	public void findallMerchantsByType() {
		List<String> findallMerchantsByType = merchantRepo.findDistinctMerchantType();
		System.out.println(findallMerchantsByType);
	}

	// =========================================================================================================

	// working
	@Test
	public void addHotlistMerchantName() {
		DependentMerchantHotlistPK id = new DependentMerchantHotlistPK();
		Dependent dependent = dependentRepo.findById("d1").get();
		id.setDependent_Id(dependent.getDependentId());
		id.setMerchant("Amazon");
		DependentMerchantHotlist dependentMerchantHotlist = new DependentMerchantHotlist(id, "Y", "M", 10000,
				dependent);

		dependentMerchantHotlistRepo.save(dependentMerchantHotlist);
	}

	// working
	@Test
	public void addHotlistMerchantType() {
		DependentMerchantHotlistPK id = new DependentMerchantHotlistPK();
		Dependent dependent = dependentRepo.findById("d2").get();
		id.setDependent_Id(dependent.getDependentId());
		id.setMerchant("pety");
		DependentMerchantHotlist dependentMerchantHotlist = new DependentMerchantHotlist(id, "N", "T", 0, dependent);

		dependentMerchantHotlistRepo.save(dependentMerchantHotlist);
	}

	// working
	@Test
	public void dependentMerchantHotlistFindById() {
		List<DependentMerchantHotlist> findByDependentId = dependentMerchantHotlistRepo.findAllByDependentId("d2");
		findByDependentId.forEach(x -> {
			System.out.println(x);
		});
	}

	// working
	@Test
	public void dependentMerchantHotlistFindByMerchant() {
		List<DependentMerchantHotlist> findAllByMerchant = dependentMerchantHotlistRepo.findAllByMerchant("pety");
		findAllByMerchant.forEach(x -> System.out.println(x));
	}

	// working
	@Test
	public void dependentMerchantHotlistMerchantContining() {
		List<DependentMerchantHotlist> findByMerchantContaining = dependentMerchantHotlistRepo
				.findByMerchantContaining("%m%");
		findByMerchantContaining.forEach(x -> System.out.println(x));
	}

	// working
	@Test
	public void findAllMerchantHotlistForDependent() {
		Dependent dependent = dependentRepo.findById("d2").get();
		Merchant merchant = merchantRepo.findById("mer3").get();
		DependentMerchantHotlist findAllMerchantHotlistForDependent = dependentMerchantHotlistCustomRepo
				.findAllMerchantHotlistForDependent(dependent, merchant, merchant.getMerchantType());
		System.out.println(findAllMerchantHotlistForDependent);

	}

	// =========================================================================================================

	// working
	@Test
	public void dependentSpendLimitInsert() {
		Dependent dependent = dependentRepo.findById("d1").get();
		DependentSpendLimit dependentSpendLimit = new DependentSpendLimit(dependent.getDependentId(), 10000, "m", 1000,
				dependent);
		dependentSpendLimitRepo.save(dependentSpendLimit);
	}

	// working
	@Test
	public void dependentSpendLimitSelect() {
		System.out.println(dependentSpendLimitRepo.findById("d2"));
	}

	// =========================================================================================================

	// working
	@Test
	public void transactionInsert() {
		Transaction transaction = new Transaction();
		transaction.setCreditAccount("OYO");
		transaction.setDebitAccount("JACK");
		transaction.setTransactionAmount(500);
		transaction.setTransactionStatus("SUCCESS");
		transaction.setTransactionType("DR");
		Transaction save = transactionRepo.save(transaction);
		System.out.println("TransactionId : " + save.getTransactionid());
	}

	// working
	@Test
	public void transactionUpdate() {
		Transaction transaction = transactionRepo.findById(2).get();
		transaction.setTransactionStatus("Approved");
		transactionRepo.save(transaction);
	}

	// working
	@Test
	public void transactionSelectByCrAc() {
		List<Transaction> findAllByCreditAccount = transactionRepo.findAllByCreditAccount("abc");
		findAllByCreditAccount.forEach(x -> System.out.println(x));
	}

	// working
	@Test
	public void transactionSelectByDrAc() {
		List<Transaction> findAllByDebitAccount = transactionRepo.findAllByDebitAccount("d2");
		findAllByDebitAccount.forEach(x -> System.out.println(x));
	}

	// working
	@Test
	public void transactionSelectByStatus() {
		List<Transaction> findAllByTransactionStatus = transactionRepo.findAllByTransactionStatus("SUCCESS");
		System.out.println(findAllByTransactionStatus);
	}

	// working
	@Test
	public void transactionSelectByType() {
		List<Transaction> findAllByTransactionType = transactionRepo.findAllByTransactionType("");
		System.out.println(findAllByTransactionType);
	}

	// working
	@Test
	public void transactionSelectByDateRange() {
		List<Transaction> findByTransactionDate = transactionCustomRepo.findByTransactionDate("24/08/2021",
				"30/08/2021", "dd/mm/yyyy");
		findByTransactionDate.forEach(x -> System.out.println(x));
	}

	// =========================================================================================================

	// working
	@Test
	public void transactionRemarksInsert() {
		Transaction transaction = transactionRepo.findById(1).get();
		TransactionRemark transactionRemark = new TransactionRemark(transaction.getTransactionid(), "Approved",
				transaction);
		transactionRemarkRepo.save(transactionRemark);
	}

	// working
	@Test
	public void transactionRemarksSelect() {
		System.out.println(transactionRemarkRepo.findById(1).get());
	}

	// =========================================================================================================

	// working
	@Test
	void getTotalSpendAmounts() {
		Master master = masterRepo.findById("m2").get();
		Set<MasterDependent> masterDependentSet = master.getMasterDependents();
		int total = 0;
		for (MasterDependent masterDependent : masterDependentSet) {
			total += masterDependent.getDependent().getDependentSpendLimit().getSpendlimit();
		}
		System.out.println("total : " + total);
	}

	// working
	@Test
	void getDebitTransactionsOfDependents() {
		Master master = masterRepo.findById("m2").get();
		for (MasterDependent masterDependent : master.getMasterDependents()) {
			String dependent_Id = masterDependent.getDependentId();
			List<Transaction> findAllByDebitAccount = transactionRepo.findAllByDebitAccount(dependent_Id);
			if (!findAllByDebitAccount.isEmpty()) {
				for (Transaction transaction : findAllByDebitAccount) {
					if (transaction.getTransactionType() == "dr") {

					}
				}
			}
		}
	}

	// working
	@Test
	void findByExactMerchantName() {
		Optional<Merchant> findByMerchantName = merchantRepo.findByMerchantName("abcd");
		System.out.println(findByMerchantName);
	}

	// working
	@Test
	void getDates() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Now : " + formatter.format(date));

		Calendar calendarD = Calendar.getInstance();
		calendarD.clear();
		calendarD.setTime(new Date());
		calendarD.set(Calendar.MILLISECOND, 0);
		calendarD.set(Calendar.SECOND, 0);
		calendarD.set(Calendar.MINUTE, 0);
		calendarD.set(Calendar.HOUR, 0);
		System.out.println("calendarD.getTime() : " + calendarD.getTime());

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(System.currentTimeMillis());
		while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
			calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
		}
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		System.out.println("calendar.getTime() : " + calendar.getTime());

		Calendar calendarM = Calendar.getInstance();
		calendarM.clear();
		calendarM.setTimeInMillis(System.currentTimeMillis());
		while (calendarM.get(Calendar.DATE) > 1) {
			calendarM.add(Calendar.DATE, -1); // Substract 1 day until first day of month.
		}
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		System.out.println("calendar.Month.getTime() : " + calendarM.getTime());

	}
}
