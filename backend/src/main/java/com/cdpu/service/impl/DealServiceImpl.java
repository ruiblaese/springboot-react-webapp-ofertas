package com.cdpu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cdpu.entity.Deal;
import com.cdpu.repository.DealRepository;
import com.cdpu.service.DealService;
import com.cdpu.util.enums.TypeDeal;

@Service
public class DealServiceImpl implements DealService{
	
	@Autowired
	DealRepository repository;

	@Override
	public Deal save(Deal d) {

		return repository.save(d);
	}

	@Override
	public Optional<Deal> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Deal> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	@Override
	public List<Deal> findAllActive() {

		return repository.findByPublishDateLessThanEqualAndEndDateGreaterThanEqualOrderById(new Date(), new Date());				
	}


	@Override
	public List<Deal> findAllActiveByType(TypeDeal type) {		
		return repository.findByPublishDateLessThanEqualAndEndDateGreaterThanEqualAndType(
				new Date(), new Date(), type);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);		
	}


}
