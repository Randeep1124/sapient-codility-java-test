package com.sapient.feeCalculator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.sapient.feeCalculator.model.Transaction;
import com.sapient.feeCalculator.model.TransactionReport;

public class Utils {

	public static String getFileExtension(String path) {

		String ext = FilenameUtils.getExtension(path);
		return ext;
	}

	public static List<Transaction> readXLSXFile(File file)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() > 0) {
				transactionList.add(new Transaction(row.getCell(0).getStringCellValue(),
						row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(),
						row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(),
						row.getCell(5).getNumericCellValue(), row.getCell(6).getStringCellValue()));
			}
		}
		workbook.close();
		return transactionList;
	}

	public static List<Transaction> readTextOrCSVFile(File file, String ext) throws IOException {
		String line = "";
		String splitBy = ext.equals("csv") ? "," : "[|]";
		List<Transaction> transactionList = new ArrayList<Transaction>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		while ((line = br.readLine()) != null) {
			String[] transaction = line.split(splitBy);
			transactionList.add(new Transaction(transaction[0], transaction[1], transaction[2], transaction[3],
					transaction[4], Double.parseDouble(transaction[5]), transaction[6]));
		}
		br.close();
		return transactionList;
	}

	public static List<TransactionReport> getTransactionReport(List<Transaction> transactionList,
			HashMap<String, List<String>> intraDayData) {
		List<TransactionReport> transactionReportList = new ArrayList<TransactionReport>();

		for (Transaction transaction : transactionList) {
			TransactionReport report = new TransactionReport();
			report.setClientId(transaction.getClientId());
			report.setTransactionType(transaction.getTransactionType());
			report.setTransactionDate(transaction.getTransactionDate());
			report.setPriorityFlag(transaction.getPriorityFlag());
			report.setProcessingFee(getProcessingFee(transaction, intraDayData));// processing fee logic need to write
			transactionReportList.add(report);
		}
		return transactionReportList;
	}

	public static HashMap<String, List<String>> getIntraDayData(List<Transaction> transactionList) {
		HashMap<String, List<String>> proceesingData = new HashMap<String, List<String>>();
		String key = "";
		for (Transaction transaction : transactionList) {
			List<String> transactionType = new ArrayList<String>();
			key = transaction.getClientId() + transaction.getSecurityId() + transaction.getTransactionDate();
			if (proceesingData.containsKey(key)) {
				transactionType = proceesingData.get(key);
				transactionType.add(transaction.getTransactionType());
				proceesingData.put(key, transactionType);
			} else {
				transactionType.add(transaction.getTransactionType());
				proceesingData.put(key, transactionType);
			}
		}
		return proceesingData;

	}

	public static double getProcessingFee(Transaction transaction, HashMap<String, List<String>> intraDayData) {
		String transactionType = transaction.getTransactionType();
		String securityId = transaction.getSecurityId();
		String clientId = transaction.getClientId();
		String transactionDate = transaction.getTransactionDate();
		String key = clientId + securityId + transactionDate;
		List<String> transactionTypeData = intraDayData.get(key);
		String priorityFlag = transaction.getPriorityFlag();
		double processingFee = 0;

		if (transactionTypeData.size() == 2) {
			if (transactionTypeData.get(0).equals("SELL") && transactionTypeData.get(1).equals("BUY")
					|| transactionTypeData.get(0).equals("BUY") && transactionTypeData.get(1).equals("SELL")) {
				processingFee = 10;
			}
		} else if ((transactionType.equals("BUY") || transactionType.equals("DEPOSIT")) && priorityFlag.equals("N")) {
			processingFee = 50;
		} else if ((transactionType.equals("SELL") || transactionType.equals("WITHDRAW")) && priorityFlag.equals("N")) {
			processingFee = 100;
		} else if (priorityFlag.equals("Y")) {
			processingFee = 500;
		}
		return processingFee;

	}

}
