package com.customerrewards.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customerrewards.dto.CustomerRewardSummaryDto;
import com.customerrewards.exception.CustomerRewardException;
import com.customerrewards.service.ICustomerRewardsSvc;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerRewardsController {

	@Autowired
	private ICustomerRewardsSvc customerRewardsSvc;

	@GetMapping("/rewards")
	public ResponseEntity<List<CustomerRewardSummaryDto>> getMonthlyCustomerRecords(
			@RequestParam(defaultValue = "monthly") final String summary, @RequestParam(defaultValue = "3") final int depth) {
       
		Instant startTime = Instant.now();
		
		validateQueryParams(summary, depth);

		final List<CustomerRewardSummaryDto> list = customerRewardsSvc.getCustomerRewardsSummary(summary, depth);

		Instant endTime = Instant.now();
		log.info("Processing Time : "+ Duration.between(startTime, endTime));
		return new ResponseEntity<>(list, HttpStatus.OK);
		
		
	}

	private void validateQueryParams(final String summary, int depth) {

		if (!summary.equalsIgnoreCase("monthly") || depth < 1) {
			throw new CustomerRewardException(HttpStatus.BAD_REQUEST.value(), "Bad Request");

		}
	}

}
