package com.cdpu.service;

import java.util.List;
import java.util.Optional;

import com.cdpu.entity.Deal;
import com.cdpu.util.enums.TypeDeal;

public interface DealService {
	
	Deal save(Deal d);
	
	Optional<Deal> findById(Long id);
	
	List<Deal> findAll();
	
	List<Deal> findAllActive();
	
	List<Deal> findAllActiveByType(TypeDeal type);
	
	void deleteById(Long id);
	
}
