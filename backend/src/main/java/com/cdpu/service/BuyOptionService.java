package com.cdpu.service;

import java.util.List;
import java.util.Optional;

import com.cdpu.entity.BuyOption;

public interface BuyOptionService {
	
	BuyOption save(BuyOption d);
	
	Optional<BuyOption> findById(Long id);
	
	List<BuyOption> findAll();
	
	List<BuyOption> findAllByDeal(Long deal);
	
	List<BuyOption> findAllActiveByDeal(Long deal);
	
	List<BuyOption> findAllBuyOptionIsNull();

}
