package com.cdpu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdpu.entity.Deal;
import com.cdpu.util.enums.TypeDeal;


public interface DealRepository extends JpaRepository<Deal, Long> {
	
	List<Deal> findByPublishDateLessThanEqualAndEndDateGreaterThanEqualOrderById(Date init, Date end);
	
	List<Deal> findByPublishDateLessThanEqualAndEndDateGreaterThanEqualAndType(Date init, Date end, TypeDeal type);
	

}
