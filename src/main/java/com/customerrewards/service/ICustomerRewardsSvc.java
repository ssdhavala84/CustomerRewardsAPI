package com.customerrewards.service;

import java.util.List;

import com.customerrewards.dto.CustomerRewardSummaryDto;

public interface ICustomerRewardsSvc {

	List<CustomerRewardSummaryDto> getCustomerRewardsSummary(String summary, Integer depth);

}
