package com.cdpu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdpu.entity.BuyOption;

public interface BuyOptionRepository extends JpaRepository<BuyOption, Long>{
	
	List<BuyOption> findAllByDeal(Long deal);
	
	
	@Query(value = "select buyo from BuyOption buyo where (buyo.deal.id = :deal) and (buyo.startDate <= :init) and (buyo.endDate >= :end) and (buyo.quantityCupom <= buyo.quantitySold)")
	List<BuyOption> findAllByDealAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndQuantityCupomLessThanQuantitySold(@Param("deal")  Long deal, @Param("init") Date init, @Param("end") Date end);
	
	List<BuyOption> findAllByDealIsNull(); 
	

}
