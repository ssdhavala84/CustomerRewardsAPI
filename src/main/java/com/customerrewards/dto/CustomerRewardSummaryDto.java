package com.customerrewards.dto;

import java.util.List;

public class CustomerRewardSummaryDto {

	private String customerName;
	private float totalPoints;

	private List<TransactionSummaryDto> transactionSummary;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public float getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(float totalPoints) {
		this.totalPoints = totalPoints;
	}

	public List<TransactionSummaryDto> getTransactionSummary() {
		return transactionSummary;
	}

	public void setTransactionSummary(List<TransactionSummaryDto> transactionSummary) {
		this.transactionSummary = transactionSummary;
	}

	@Override
	public String toString() {
		return "CustomerRewardSummaryDto [customerName=" + customerName + ", totalPoints=" + totalPoints
				+ ", transactionSummary=" + transactionSummary + "]";
	}
	
	

}