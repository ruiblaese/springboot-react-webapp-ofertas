package com.cdpu.service;

import java.util.List;
import java.util.Optional;

import com.cdpu.entity.BuyOption;
import com.cdpu.entity.Deal;

public interface BuyOptionService {
	
	BuyOption save(BuyOption d);
	
	Optional<BuyOption> findById(Long id);
	
	List<BuyOption> findAll();
	
	List<BuyOption> findAllByDeal(Deal deal);
	
	List<BuyOption> findAllActiveByDeal(Deal deal);
	
	List<BuyOption> findAllBuyOptionIsNull();

}
