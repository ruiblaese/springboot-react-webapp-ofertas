package com.cdpu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdpu.dto.BuyOptionDTO;
import com.cdpu.entity.BuyOption;
import com.cdpu.entity.Deal;
import com.cdpu.response.Response;
import com.cdpu.service.BuyOptionService;
import com.cdpu.service.DealService;
import com.cdpu.util.ConvertEntities;

@RestController
@RequestMapping("buy-option")
public class BuyOptionController {
	
	@Autowired
	private BuyOptionService service;	
	@Autowired
	private DealService serviceDeal;
	
	
	@PostMapping
	public ResponseEntity<Response<BuyOptionDTO>> create(@Valid @RequestBody BuyOptionDTO dto, BindingResult result) {
	
		Response<BuyOptionDTO> response = new Response<BuyOptionDTO>();
				
		if (dto.getDeal() != null) {
			Optional<Deal> deal = serviceDeal.findById(dto.getDeal());
			if (!deal.isPresent()) {
				result.addError(new ObjectError("Deal", "Oferta não encontrada"));
			}
		}
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}
		
		dto.setQuantitySold(0L);
		BuyOption buyo = service.save(ConvertEntities.convertBuyOptionDtoToBuyOption(dto));

		response.setData(ConvertEntities.convertBuyOptionToBuyOptionDto(buyo));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping
	public ResponseEntity<Response<BuyOptionDTO>> update(@Valid @RequestBody BuyOptionDTO dto, BindingResult result) {
	
		Response<BuyOptionDTO> response = new Response<BuyOptionDTO>();
		
		Optional<BuyOption> buyoDb = service.findById(dto.getId());

		if (!buyoDb.isPresent()) {
			result.addError(new ObjectError("BuyOptionChanged", "Opção de Oferta não encontrada"));
		}
		
		if (dto.getDeal() != null) {
			Optional<Deal> deal = serviceDeal.findById(dto.getId());
			if (!deal.isPresent()) {
				result.addError(new ObjectError("BuyOptionChanged", "Oferta não encontrada"));
			}else if (buyoDb.get().getDeal().getId().compareTo(dto.getDeal()) != 0) {
				result.addError(new ObjectError("BuyOptionChanged", "Você não pode alterar a Oferta"));
			}
		}
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}
		
		/* nao permite editar os campos abaixo */				
		dto.setQuantitySold(buyoDb.get().getQuantitySold());		

		BuyOption buyo = service.save(ConvertEntities.convertBuyOptionDtoToBuyOption(dto));

		response.setData(ConvertEntities.convertBuyOptionToBuyOptionDto(buyo));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}	
	
	
	@GetMapping()
	public ResponseEntity<Response<List<BuyOptionDTO>>> findAll(){
		
		ArrayList<BuyOption> list = (ArrayList<BuyOption>) service.findAll();
		
		List<BuyOptionDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertBuyOptionToBuyOptionDto(i)));
		
		Response<List<BuyOptionDTO>> response = new Response<List<BuyOptionDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}	
	
	@GetMapping(value = "/deal/{dealId}")
	public ResponseEntity<Response<List<BuyOptionDTO>>> findAllByDeal(@PathVariable("dealId") Long dealId){
		Deal deal = new Deal();
		deal.setId(dealId);
		ArrayList<BuyOption> list = (ArrayList<BuyOption>) service.findAllByDeal(deal);
		
		List<BuyOptionDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertBuyOptionToBuyOptionDto(i)));
		
		Response<List<BuyOptionDTO>> response = new Response<List<BuyOptionDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}		
				

}
