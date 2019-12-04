package com.cdpu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cdpu.entity.Deal;
import com.cdpu.repository.DealRepository;
import com.cdpu.service.DealService;

public class DealServiceImpl implements DealService{
	
	@Autowired
	DealRepository repository;

	@Override
	public Deal save(Deal d) {

		return repository.save(d);
	}


}
