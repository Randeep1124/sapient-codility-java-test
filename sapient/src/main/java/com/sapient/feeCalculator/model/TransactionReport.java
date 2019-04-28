package com.sapient.feeCalculator.model;

public class TransactionReport {

	private String clientId;
	private String transactionType;
	private String transactionDate;
	private String priorityFlag;
	private double processingFee;

	public TransactionReport(String clientId, String transactionType, String transactionDate, String priorityFlag,
			double processingFee) {
		super();
		this.clientId = clientId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.priorityFlag = priorityFlag;
		this.processingFee = processingFee;
	}

	public TransactionReport() {
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public double getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}

	@Override
	public String toString() {
		return "TransactionReport [clientId=" + clientId + ", transactionType=" + transactionType + ", transactionDate="
				+ transactionDate + ", priorityFlag=" + priorityFlag + ", processingFee=" + processingFee + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((priorityFlag == null) ? 0 : priorityFlag.hashCode());
		long temp;
		temp = Double.doubleToLongBits(processingFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionReport other = (TransactionReport) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (priorityFlag == null) {
			if (other.priorityFlag != null)
				return false;
		} else if (!priorityFlag.equals(other.priorityFlag))
			return false;
		if (Double.doubleToLongBits(processingFee) != Double.doubleToLongBits(other.processingFee))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
