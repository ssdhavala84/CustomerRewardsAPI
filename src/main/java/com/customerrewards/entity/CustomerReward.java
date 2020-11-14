package com.customerrewards.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerReward {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	@Column(nullable = false)
	private String customerName;
	@Column(nullable = false)
	private float amount;
	@Column(nullable = false)
	private LocalDate transactionDate;
	@Column(nullable = false)
	private String transactionMonth;

	public CustomerReward() {

	}

	public CustomerReward(int transactionId, String customerName, float amount, LocalDate transactionDate,
			String transactionMonth) {
		super();
		this.transactionId = transactionId;
		this.customerName = customerName;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.transactionMonth = transactionMonth;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionMonth() {
		return transactionMonth;
	}

	public void setTransactionMonth(String transactionMonth) {
		this.transactionMonth = transactionMonth;
	}

	@Override
	public String toString() {
		return "CustomerReward [transactionId=" + transactionId + ", customerName=" + customerName + ", amount="
				+ amount + "transactionDate=" + transactionDate + ", transactionMonth=" + transactionMonth + "]";
	}

}
