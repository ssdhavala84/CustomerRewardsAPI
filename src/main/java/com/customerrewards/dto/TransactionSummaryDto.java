package com.customerrewards.dto;



public class TransactionSummaryDto {

	private String month;
	private float rewardsPoints;

	public TransactionSummaryDto() {

	}

	public TransactionSummaryDto(String month, float rewardsPoints) {
		super();
		this.month = month;
		this.rewardsPoints = rewardsPoints;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public float getRewardsPoints() {
		return rewardsPoints;
	}

	public void setRewardsPoints(float rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}

}
