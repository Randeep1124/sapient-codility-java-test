package com.sapient.feeCalculator.main;

import java.io.File;
import java.util.List;
import com.sapient.feeCalculator.model.Transaction;
import com.sapient.feeCalculator.model.TransactionReport;
import com.sapient.feeCalculator.service.FeeCalculatorService;
import com.sapient.feeCalculator.service.FeeCalculatorServiceImpl;
import com.sapient.feeCalculator.util.Utils;

public class TransactionFeeCalculator {

	public static void main(String[] args) throws Exception {
		FeeCalculatorService calculatorService = new FeeCalculatorServiceImpl();
		File file = calculatorService.getFileFromResources("dataPipe.txt");
		String ext = Utils.getFileExtension(file.getAbsolutePath());
		List<Transaction> transactionList;
		List<TransactionReport> transactionReportList;

		transactionList = calculatorService.getTransactionList(ext, file);
		transactionReportList = calculatorService.getTransactionReport(transactionList);

		System.out
				.println("Client ID\t" + "Transaction Type\t" + "Transaction Date\t" + "Priority\t" + "Processing Fee");
		for (TransactionReport report : transactionReportList) {
			System.out.println(report.getClientId() + "\t" + report.getTransactionType() + "\t"
					+ report.getTransactionDate() + "\t" + report.getPriorityFlag() + "\t" + report.getProcessingFee());
		}

	}
}
