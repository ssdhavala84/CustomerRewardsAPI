package com.customerrewards.service;

import java.util.List;

import com.customerrewards.dto.CustomerRewardSummary;

public interface ICustomerRewardsSvc {

	List<CustomerRewardSummary> getCustomerRewardsSummary(String summary, Integer depth);

}
