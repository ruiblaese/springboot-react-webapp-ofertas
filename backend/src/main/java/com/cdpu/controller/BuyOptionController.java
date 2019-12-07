package com.cdpu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdpu.dto.BuyOptionDTO;
import com.cdpu.entity.BuyOption;
import com.cdpu.response.Response;
import com.cdpu.service.BuyOptionService;
import com.cdpu.util.ConvertEntities;

@RestController
@RequestMapping("buy-option")
public class BuyOptionController {
	
	@Autowired
	private BuyOptionService service;	
	
	@GetMapping()
	public ResponseEntity<Response<List<BuyOptionDTO>>> findAll(){
		
		ArrayList<BuyOption> list = (ArrayList<BuyOption>) service.findAll();
		
		List<BuyOptionDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertBuyOptionToBuyOptionDto(i)));
		
		Response<List<BuyOptionDTO>> response = new Response<List<BuyOptionDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}	

}
