package com.customerrewards.dto;

import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionSummaryDto {

	private String month;
	private float rewardsPoints;

}
