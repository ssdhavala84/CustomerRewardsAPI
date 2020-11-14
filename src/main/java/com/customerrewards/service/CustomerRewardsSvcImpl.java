package com.customerrewards.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customerrewards.entity.CustomerReward;

import com.customerrewards.dto.CustomerRewardSummaryDto;
import com.customerrewards.dto.TransactionSummaryDto;
import com.customerrewards.repository.ICustomerRewardsRepository;
import com.customerrewards.exception.CustomerRewardNotFoundException;

@Service
public class CustomerRewardsSvcImpl implements ICustomerRewardsSvc {

	@Autowired
	private ICustomerRewardsRepository customerRewardRepository;

	@Override
	public List<CustomerRewardSummaryDto> getCustomerRewardsSummary(String summary, Integer depth) {
		final List<CustomerReward> customerRewards = (List<CustomerReward>) customerRewardRepository
				.findAllCustomerRewards(getTransactionMonths(depth));

		if (customerRewards.isEmpty())
			throw new CustomerRewardNotFoundException(HttpStatus.NOT_FOUND.value(), "CutomerReward Not Found");
		try {
			return convertEntityToDto(customerRewards);
		} catch (Exception e) {
			throw new CustomerRewardNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Internal Server error during Entity To Dto Transformation");
		}

	}

	private List<CustomerRewardSummaryDto> convertEntityToDto(List<CustomerReward> customerRewards) {

		Map<String, List<Map<String, Float>>> custMap = convertEntityToMap(customerRewards);

		List<CustomerRewardSummaryDto> crSummaryDto = new ArrayList<>();

		for (Entry<String, List<Map<String, Float>>> entry : custMap.entrySet()) {

			float totalPoints = 0;
			CustomerRewardSummaryDto crDto = new CustomerRewardSummaryDto();

			String customerName = entry.getKey();

			List<Map<String, Float>> transactionsList = entry.getValue();
			List<TransactionSummaryDto> transactionsDto = new ArrayList<>();
			for (Map<String, Float> monthMap : transactionsList) {

				for (Entry<String, Float> k : monthMap.entrySet()) {

					String month = k.getKey();
					Float rewardPoints = k.getValue();
					transactionsDto.add(new TransactionSummaryDto(month, rewardPoints));
					totalPoints = totalPoints + rewardPoints;

				}
			}

			crDto.setCustomerName(customerName);
			crDto.setTotalPoints(totalPoints);
			crDto.setTransactionSummary(transactionsDto);
			crSummaryDto.add(crDto);
		}
		return crSummaryDto;
	}

	private Map<String, List<Map<String, Float>>> convertEntityToMap(List<CustomerReward> customerRewards) {

		Map<String, List<Map<String, Float>>> customerMap = new HashMap<>();

		for (CustomerReward record : customerRewards) {

			String name = record.getCustomerName();
			String month = record.getTransactionMonth();
			float amount = record.getAmount();
			float rewardPoints = getRewardPoints(amount);
			if (customerMap.containsKey(name)) {

				List<Map<String, Float>> monthMapList = customerMap.get(name);
				boolean monthExists = false;
				Map<String, Float> re = new HashMap<>();
				for (Map<String, Float> monthMap : monthMapList) {
					if (monthMap.containsKey(month)) {
						monthExists = true;
						re = monthMap;
					}
				}

				if (monthExists) {
					Float dv = re.get(month);
					re.put(month, dv + rewardPoints);
				} else {
					Map<String, Float> r = new HashMap<>();
					r.put(month, rewardPoints);
					monthMapList.add(r);
				}

			} else {
				Map<String, Float> monthMap = new HashMap<>();
				monthMap.put(month, rewardPoints);
				List<Map<String, Float>> monthMapList = new ArrayList<>();
				monthMapList.add(monthMap);
				customerMap.put(name, monthMapList);
			}
		}

		return customerMap;
	}

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

	private List<String> getTransactionMonths(int noOfMonths) {

		Month month = LocalDate.now().getMonth();

		List<String> trxMonths = new ArrayList<>();
		for (int i = 1; i <= noOfMonths; i++) {

			trxMonths.add(month.toString());
			month = month.minus(1);
		}
		return trxMonths;
	}
}
