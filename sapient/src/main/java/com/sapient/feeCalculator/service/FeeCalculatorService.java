package com.sapient.feeCalculator.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.sapient.feeCalculator.model.Transaction;
import com.sapient.feeCalculator.model.TransactionReport;

public interface FeeCalculatorService {

	File getFileFromResources(String fileName);

	List<Transaction> getTransactionList(String ext, File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException;

	List<TransactionReport> getTransactionReport(List<Transaction> transactionList);

}
