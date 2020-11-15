package com.customerrewards.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customerrewards.entity.CustomerReward;

import com.customerrewards.dto.CustomerRewardSummaryDto;

import lombok.extern.slf4j.Slf4j;

import com.customerrewards.exception.CustomerRewardException;
import com.customerrewards.mapper.CustomerRewardsMapper;
import com.customerrewards.repository.ICustomerRewardsRepository;

@Service
@Slf4j
public class CustomerRewardsSvcImpl implements ICustomerRewardsSvc {

	@Autowired
	public ICustomerRewardsRepository customerRewardRepository;
	private CustomerRewardsMapper data = new CustomerRewardsMapper();

	@Override
	public List<CustomerRewardSummaryDto> getCustomerRewardsSummary(String summary, Integer depth) {
		Instant startTime = Instant.now();
		final List<CustomerReward> customerRewards = (List<CustomerReward>) customerRewardRepository
				.findAllCustomerRewards(getMonths(depth));
		Instant endTime = Instant.now();
		log.info("Processing Time Get Entity: " + Duration.between(startTime, endTime));
		if (customerRewards.isEmpty())
			throw new CustomerRewardException(HttpStatus.NOT_FOUND.value(), "CutomerReward Not Found");
		try {
			return data.convertEntityToDto(customerRewards);
		} catch (Exception e) {
			throw new CustomerRewardException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Internal Server error during Entity To Dto Transformation");
		}

	}

	public List<String> getMonths(int noOfMonths) {

		Month month = LocalDate.now().getMonth();

		List<String> trxMonths = new ArrayList<>();
		for (int i = 1; i <= noOfMonths; i++) {

			trxMonths.add(month.toString());
			month = month.minus(1);
		}
		return trxMonths;
	}

}
