package com.customerrewards.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

}
