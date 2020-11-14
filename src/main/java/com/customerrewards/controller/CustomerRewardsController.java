package com.customerrewards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customerrewards.dto.CustomerRewardSummaryDto;
import com.customerrewards.exception.CustomerRewardNotFoundException;
import com.customerrewards.service.ICustomerRewardsSvc;

@RestController
@RequestMapping("/customers")
public class CustomerRewardsController {

	@Autowired
	private ICustomerRewardsSvc customerRewardsSvc;

	@GetMapping("/rewards")
	public ResponseEntity<List<CustomerRewardSummaryDto>> getMonthlyCustomerRecords(
			@RequestParam(defaultValue = "monthly") String summary, @RequestParam(defaultValue = "3") Integer depth) {

		validateQueryParams(summary, depth);

		final List<CustomerRewardSummaryDto> list = customerRewardsSvc.getCustomerRewardsSummary(summary, depth);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private void validateQueryParams(String summary, int depth) {

		if (!summary.equalsIgnoreCase("monthly") || depth < 1) {
			throw new CustomerRewardNotFoundException(HttpStatus.BAD_REQUEST.value(), "Bad Request");

		}
	}

}
