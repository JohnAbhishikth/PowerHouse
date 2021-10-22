package com.lti.solvathon.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lti.solvathon.entity.Transaction;

@Repository
public class TransactionCustomRepoImpl implements TransactionCustomRepo {

	@Autowired
	TransactionRepo transactionRepo;

	@Override
	public List<Transaction> findByTransactionDate(String date_AfterThan, String date_BeforeThan, String DateFormat) {

		SimpleDateFormat parseFormat = new SimpleDateFormat(DateFormat);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		try {
			if (date_AfterThan == null) {
				Date date_Before = parseFormat.parse(date_BeforeThan);
				String date = format.format(date_Before);
				return transactionRepo.findAllByTransactionDateBefore(date);

			}
			if (date_BeforeThan == null) {
				Date date_After = parseFormat.parse(date_AfterThan);
				String date = format.format(date_After);
				return transactionRepo.findAllByTransactionDateAfter(date);

			}
			if (date_AfterThan != null && date_BeforeThan != null) {
				Date date_Before = parseFormat.parse(date_BeforeThan);
				Date date_After = parseFormat.parse(date_AfterThan);
				String date_B = format.format(date_Before);
				String date_A = format.format(date_After);
				return transactionRepo.findAllByTransactionDateInRange(date_A, date_B);

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
