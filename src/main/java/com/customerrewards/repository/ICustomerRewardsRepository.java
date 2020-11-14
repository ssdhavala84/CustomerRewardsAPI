package com.customerrewards.repository;
import java.util.Collection;
import java.util.List;

import com.customerrewards.entity.CustomerReward;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
public interface ICustomerRewardsRepository extends CrudRepository<CustomerReward, Long> {

	@Query("SELECT cr FROM CustomerReward cr WHERE cr.transactionMonth IN :monthsList")
	Collection<CustomerReward> findAllCustomerRewards(List<String> monthsList);
}
