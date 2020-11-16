package com.customerrewards.dto;
import java.util.List;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CustomerRewardSummary {

	private String customerName;
	private float totalPoints;
	private List<TransactionSummaryDto> transactionSummary;

}