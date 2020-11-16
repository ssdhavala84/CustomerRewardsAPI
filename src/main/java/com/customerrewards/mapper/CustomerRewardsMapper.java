package com.customerrewards.mapper;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import com.customerrewards.dto.CustomerRewardSummary;
import com.customerrewards.dto.TransactionSummaryDto;
import com.customerrewards.entity.CustomerReward;
import com.customerrewards.repository.ICustomerRewardsRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CustomerRewardsMapper {
	@Autowired
	public ICustomerRewardsRepository customerRewardRepository;

	/**
	 * <p>Transforms the List of CustomerRewards data Entity  records into a Dto List
	 *    This helps in in transformation transformation of flat data format from table into 
	 *    hierarchical response message format. 
	 *    
	 *    As part of the transformation it creates a summary list of cumulative reward points 
	 *    for each customer for the number of months requested. 
	 *    default summary data will be provided for 3 months summary list
	 *    reward points
	 * </p>
	 * @param List<CustomerReward> representing flat data format representing data from database table
	 * @return List<CustomerRewardSummary> object representing hierarchical response message format 
	 */
	public List<CustomerRewardSummary> convertEntityToDto(final List<CustomerReward> customerRewards) {
		Instant startTime = Instant.now();

		final Map<String, List<Map<String, Float>>> custMap = convertEntityToMap(customerRewards);

		final List<CustomerRewardSummary> crSummaryList = new ArrayList<>();

		for (Entry<String, List<Map<String, Float>>> entry : custMap.entrySet()) {

			float totalPoints = 0;

			String customerName = entry.getKey();

			final List<Map<String, Float>> transactionsList = entry.getValue();
			final List<TransactionSummaryDto> transactionsDto = new ArrayList<>();
			for (Map<String, Float> monthMap : transactionsList) {

				for (Entry<String, Float> monthEntry : monthMap.entrySet()) {

					String month = monthEntry.getKey();
					float rewardPoints = monthEntry.getValue();
					transactionsDto.add(new TransactionSummaryDto(month, rewardPoints));
					totalPoints = totalPoints + rewardPoints;

				}
			}
			
			crSummaryList.add(CustomerRewardSummary.builder().customerName(customerName).totalPoints(totalPoints)
					.transactionSummary(transactionsDto).build());
		}
		
		log.info("Time take for transformation : " + Duration.between(startTime, Instant.now()));
		return crSummaryList;
	}

	private Map<String, List<Map<String, Float>>> convertEntityToMap(final List<CustomerReward> customerRewards) {

		final Map<String, List<Map<String, Float>>> customerMap = new HashMap<>();

		for (CustomerReward customerReward : customerRewards) {

			String name = customerReward.getCustomerName();
			String month = customerReward.getTransactionMonth();
			float amount = customerReward.getAmount();
			float rewardPoints = getRewardPoints(amount);

			if (customerMap.containsKey(name)) {

				final List<Map<String, Float>> monthMapList = customerMap.get(name);
				boolean monthExists = false;
				Map<String, Float> monthMap = new HashMap<>();
				for (Map<String, Float> map : monthMapList) {
					if (map.containsKey(month)) {
						monthExists = true;
						monthMap = map;
					}
				}

				if (monthExists) {
					float dv = monthMap.get(month);
					monthMap.put(month, dv + rewardPoints);
				} else {
					Map<String, Float> r = new HashMap<>();
					r.put(month, rewardPoints);
					monthMapList.add(r);
				}

			} else {
				final Map<String, Float> monthMap = new HashMap<>();
				monthMap.put(month, rewardPoints);
				final List<Map<String, Float>> monthMapList = new ArrayList<>();
				monthMapList.add(monthMap);
				customerMap.put(name, monthMapList);
			}
		}

		return customerMap;
	}
	
	/**
	 * <p>This Method takes transactionAmount as input and calculates the Reward points for the amount 
	 * based on transaction AmountOver50_rule and AmountOver100_rule 
	 * AmountOver50_rule: rewards points increment by 1
	 * AmountOver100_rule: rewards points = 2 times (transaction Amount - 100)
	 * </p>
	 * @param transactionAmount
	 * @return rewardPoints 
	 */

	private float getRewardPoints(float transactionAmount) {

		float points = 0;
		// AmountOver50_rule
		if (transactionAmount > 50)
			points = points + 1;
		// AmountOver100_rule
		if (transactionAmount > 100) {
			points = points + 2 * (transactionAmount - 100);
		}

		return points;
	}

}