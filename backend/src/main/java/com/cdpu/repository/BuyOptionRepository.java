package com.cdpu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdpu.entity.BuyOption;

public interface BuyOptionRepository extends JpaRepository<BuyOption, Long>{
	
	List<BuyOption> findAllByDeal(Long deal);
	
	
	List<BuyOption> findAllByDealAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndQuantityCupomLessThanQuantitySold(Long deal, Date init, Date end);
	
	List<BuyOption> findAllByDealIsNull(); 
	

}
