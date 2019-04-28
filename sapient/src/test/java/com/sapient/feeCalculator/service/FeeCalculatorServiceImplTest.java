package com.sapient.feeCalculator.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapient.feeCalculator.model.Transaction;
import com.sapient.feeCalculator.model.TransactionReport;
import com.sapient.feeCalculator.util.Utils;

public class FeeCalculatorServiceImplTest {

	@Mock
	FeeCalculatorService feeCalculatorService;

	@InjectMocks
	FeeCalculatorServiceImpl feeCalculatorServiceImpl;

	@Before
	public void initilize() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getTransactionList() throws EncryptedDocumentException, InvalidFormatException, IOException {

		Transaction transaction = new Transaction("SAPEXTXN1", "GS", "ICICI", "BUY", "23/11/2013", 101.9, "Y");
		List<Transaction> transactionList1 = new ArrayList<Transaction>();
		transactionList1.add(transaction);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("dataPipeTest.txt").getFile());
		String ext = Utils.getFileExtension(file.getAbsolutePath());
		List<Transaction> transactionList2 = new ArrayList<Transaction>();
		switch (ext) {
		case "xlsx":
			transactionList2 = Utils.readXLSXFile(file);
			assertEquals(transactionList1, transactionList2);
			break;
		case "xls":
			transactionList2 = Utils.readXLSXFile(file);
			assertEquals(transactionList1, transactionList2);
			break;
		case "csv":
			transactionList2 = Utils.readTextOrCSVFile(file, ext);
			assertEquals(transactionList1, transactionList2);
			break;
		case "txt":
			transactionList2 = Utils.readTextOrCSVFile(file, ext);
			assertEquals(transactionList1, transactionList2);
			break;
		default:
			System.out.println("Please provide valid File");
		}
	}

	@Test
	public void getTransactionReport() {

		Transaction transaction1 = new Transaction("SAPEXTXN1", "GS", "ICICI", "BUY", "23/11/2013", 101.9, "Y");
		Transaction transaction2 = new Transaction("SAPEXTXN2", "AS", "REL", "SELL", "20/11/2013", 121.9, "N");

		List<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList.add(transaction1);
		transactionList.add(transaction2);

		TransactionReport report1 = new TransactionReport("GS", "BUY", "23/11/2013", "Y", 500.0);
		TransactionReport report2 = new TransactionReport("AS", "SELL", "20/11/2013", "N", 100.0);

		List<TransactionReport> transactionReportListActual = new ArrayList<TransactionReport>();
		transactionReportListActual.add(report1);
		transactionReportListActual.add(report2);

		List<TransactionReport> transactionReportListExpected;
		HashMap<String, List<String>> intraDayData;

		intraDayData = Utils.getIntraDayData(transactionList);
		transactionReportListExpected = Utils.getTransactionReport(transactionList, intraDayData);

		assertEquals(transactionReportListActual, transactionReportListExpected);

	}

}
