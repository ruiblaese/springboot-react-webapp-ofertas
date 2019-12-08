package com.cdpu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdpu.entity.BuyOption;
import com.cdpu.entity.Deal;
import com.cdpu.repository.BuyOptionRepository;
import com.cdpu.service.BuyOptionService;

@Service
public class BuyOptionServiceImpl implements BuyOptionService {

	@Autowired
	BuyOptionRepository repository;

	@Override
	public BuyOption save(BuyOption bo) {
		return repository.save(bo);
	}

	@Override
	public Optional<BuyOption> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<BuyOption> findAll() {
		return repository.findAll();
	}

	@Override
	public List<BuyOption> findAllByDeal(Deal deal) {
		return repository.findAllByDeal(deal);
	}

	@Override
	public List<BuyOption> findAllActiveByDeal(Long deal) { 
		return repository.findAllByDealAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndQuantityCupomLessThanQuantitySold(deal,new Date(),new Date());
	}

	@Override
	public List<BuyOption> findAllBuyOptionIsNull() {
		// TODO Auto-generated method stub
		return null;
	}

}
