package com.cdpu.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.cdpu.dto.BuyOptionDTO;
import com.cdpu.dto.DealDTO;
import com.cdpu.dto.UserDTO;
import com.cdpu.entity.BuyOption;
import com.cdpu.entity.Deal;
import com.cdpu.entity.User;
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
		
		long diff = deal.getEndDate().getTime() - deal.getPublishDate().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		dto.setValidity(diffDays);
		
		return dto;
	}
	
	public static Deal convertDealDtoToDeal(DealDTO dto) {
		
		Deal deal = new  Deal();
		deal.setCreateDate(dto.getCreateDate());		
		deal.setId(dto.getId());
		deal.setPublishDate(dto.getPublishDate());
		deal.setText(dto.getText());
		deal.setTitle(dto.getTitle());
		deal.setTotalSold(dto.getTotalSold());
		deal.setType(TypeDeal.valueOf(dto.getType()));
		deal.setUrl(dto.getUrl());
		
		LocalDateTime localDateTime = dto.getPublishDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();		
		Date dateIncremented = Date.from(localDateTime.plusDays(dto.getValidity()).atZone(ZoneId.systemDefault()).toInstant());		
		deal.setEndDate(dateIncremented);
		
		return deal;
	}
	
	public static BuyOptionDTO convertBuyOptionToBuyOptionDto(BuyOption buyo) {
		
		BuyOptionDTO dto = new BuyOptionDTO();
		dto.setId(buyo.getId());
		dto.setDeal(buyo.getDeal().getId());
		dto.setStartDate(buyo.getStartDate());
		dto.setEndDate(buyo.getEndDate());
		dto.setNormalPrice(buyo.getNormalPrice());
		dto.setPercentageDiscount(buyo.getPercentageDiscount());
		dto.setSalePrice(buyo.getSalePrice());
		dto.setQuantityCupom(buyo.getQuantityCupom());
		dto.setQuantitySold(buyo.getQuantitySold());
		dto.setTitle(buyo.getTitle());
		
		return dto;		
	}
	
	public static BuyOption convertBuyOptionDtoToBuyOption(BuyOptionDTO dto) {
		
		Deal deal = new Deal();
		deal.setId(dto.getDeal());
		
		BuyOption buyo = new BuyOption();
		buyo.setId(dto.getId());
		buyo.setDeal(deal);
		buyo.setStartDate(dto.getStartDate());
		buyo.setEndDate(dto.getEndDate());
		buyo.setNormalPrice(dto.getNormalPrice());		
		buyo.setPercentageDiscount(dto.getPercentageDiscount());
		buyo.setSalePrice(dto.getSalePrice());
		buyo.setQuantityCupom(dto.getQuantityCupom());
		buyo.setQuantitySold(dto.getQuantitySold());
		buyo.setTitle(dto.getTitle());
		
		return buyo;		
	}	
	
	public static UserDTO convertUserToUserDto(User user) {
		
		UserDTO dto= new UserDTO();		
		dto.setId(user.getId());		
		dto.setEmail(user.getEmail());
		dto.setName(user.getName());		
		
		
		return dto;
	}
	
	public static User convertUserDtoToUser(UserDTO dto) {
		
		User user = new User();		
		user.setId(dto.getId());		
		user.setEmail(dto.getEmail());
		user.setName(dto.getName());
		
		return user;
	}

}
