package com.cdpu.util;

import com.cdpu.dto.DealDTO;
import com.cdpu.entity.Deal;
import com.cdpu.util.enums.TypeDeal;

public class ConvertEntities {
	
	public static DealDTO convertDealToDealDto(Deal deal) {
				
		DealDTO dto = new DealDTO();
		dto.setCreateDate(deal.getCreateDate());
		dto.setEndDate(deal.getEndDate());
		dto.setId(deal.getId());
		dto.setPublishDate(deal.getPublishDate());
		dto.setText(deal.getText());
		dto.setTitle(deal.getTitle());
		dto.setTotalSold(deal.getTotalSold());
		dto.setType(deal.getType().name());
		dto.setUrl(deal.getUrl());
		
		return dto;
	}
	
	public static Deal convertDealDtoToDeal(DealDTO dto) {
		
		Deal deal = new  Deal();
		deal.setCreateDate(dto.getCreateDate());
		deal.setEndDate(dto.getEndDate());
		deal.setId(dto.getId());
		deal.setPublishDate(dto.getPublishDate());
		deal.setText(dto.getText());
		deal.setTitle(dto.getTitle());
		deal.setTotalSold(dto.getTotalSold());
		deal.setType(TypeDeal.valueOf(dto.getType()));
		deal.setUrl(dto.getUrl());
		
		return deal;
	}

}
