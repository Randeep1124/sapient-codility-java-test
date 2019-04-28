package com.sapient.feeCalculator.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.sapient.feeCalculator.model.Transaction;
import com.sapient.feeCalculator.model.TransactionReport;
import com.sapient.feeCalculator.util.Utils;

public class FeeCalculatorServiceImpl implements FeeCalculatorService {

	public File getFileFromResources(String fileName) {

		ClassLoader classLoader = getClass().getClassLoader();

		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return new File(resource.getFile());
		}

	}

	public List<Transaction> getTransactionList(String ext, File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		switch (ext) {
		case "xlsx":
			transactionList = Utils.readXLSXFile(file);
			break;
		case "xls":
			transactionList = Utils.readXLSXFile(file);
			break;
		case "csv":
			transactionList = Utils.readTextOrCSVFile(file, ext);
			break;
		case "txt":
			transactionList = Utils.readTextOrCSVFile(file, ext);
			break;
		default:
			System.out.println("Please provide valid File");
		}

		return transactionList;
	}

	@Override
	public List<TransactionReport> getTransactionReport(List<Transaction> transactionList) {
		List<TransactionReport> transactionReportList;
		HashMap<String, List<String>> intraDayData;

		intraDayData = Utils.getIntraDayData(transactionList);
		transactionReportList = Utils.getTransactionReport(transactionList, intraDayData);
		return transactionReportList;
	}

}
