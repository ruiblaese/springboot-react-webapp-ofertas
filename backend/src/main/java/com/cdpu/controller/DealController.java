package com.cdpu.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cdpu.dto.DealDTO;
import com.cdpu.entity.Deal;
import com.cdpu.response.Response;
import com.cdpu.service.DealService;
import com.cdpu.util.ConvertEntities;
import com.cdpu.util.enums.TypeDeal;

@RestController
@RequestMapping("deal")
public class DealController {
	
	@Autowired
	private DealService service;
	
	@PostMapping
	public ResponseEntity<Response<DealDTO>> create(@Valid @RequestBody DealDTO dto, 
			BindingResult result){
		
		Response<DealDTO> response = new Response<DealDTO>();
		
		if (result.hasErrors()) {
			System.out.println(result);
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
				
		Deal dealInsert = ConvertEntities.convertDealDtoToDeal(dto);		
		dealInsert.setCreateDate(new Date());
		dealInsert.setTotalSold(0L);		
		
		Deal deal = service.save(dealInsert);		
		deal.setUrl("/oferta/" + String.valueOf(deal.getId()));
		deal = service.save(deal);		
				
		response.setData(ConvertEntities.convertDealToDealDto(deal));		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);		
	}
	
	@GetMapping(value = "{dealId}")
	public ResponseEntity<Response<DealDTO>> findById(@PathVariable("dealId") Long dealId){
		
		Optional<Deal> deal = service.findById(dealId);
						
		Response<DealDTO> response = new Response<DealDTO>();		
		if (deal.isPresent()) {			
			response.setData(ConvertEntities.convertDealToDealDto(deal.get()));
		}		
		
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping()
	public ResponseEntity<Response<List<DealDTO>>> findAll(){
		
		ArrayList<Deal> list = (ArrayList<Deal>) service.findAll();
		
		List<DealDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertDealToDealDto(i)));
		
		Response<List<DealDTO>> response = new Response<List<DealDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}		
	
	@GetMapping(value = "/active/")
	public ResponseEntity<Response<List<DealDTO>>> findAllActive(){
		
		ArrayList<Deal> list = (ArrayList<Deal>) service.findAllActive();
		
		List<DealDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertDealToDealDto(i)));
		
		Response<List<DealDTO>> response = new Response<List<DealDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping(value = "/active/type/{type}")
	public ResponseEntity<Response<List<DealDTO>>> findAllActiveByType(
			@PathVariable("type") String type){		
		
		ArrayList<Deal> list = (ArrayList<Deal>) service.findAllActiveByType(TypeDeal.fromString(type));
		
		List<DealDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertDealToDealDto(i)));
		
		Response<List<DealDTO>> response = new Response<List<DealDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<Response<DealDTO>> update(@Valid @RequestBody DealDTO dto, BindingResult result) {

		Response<DealDTO> response = new Response<DealDTO>();

		Optional<Deal> wi = service.findById(dto.getId());

		if (!wi.isPresent()) {
			result.addError(new ObjectError("Deal", "Oferta não encontrada"));
		}		

		if (result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}		
		/* nao permite editar os campos abaixo */
		dto.setCreateDate(wi.get().getCreateDate());
		dto.setUrl(wi.get().getUrl());
		dto.setTotalSold(wi.get().getTotalSold());

		Deal saved = service.save(ConvertEntities.convertDealDtoToDeal(dto));		

		response.setData(ConvertEntities.convertDealToDealDto(saved));
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(value = "/{dealId}")
	public ResponseEntity<Response<String>> delete(@PathVariable("dealId") Long dealId) {
		Response<String> response = new Response<String>();

		Optional<Deal> wi = service.findById(dealId);

		if (!wi.isPresent()) {
			response.getErrors().add("Deal de id " + dealId + " não encontrada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		service.deleteById(dealId);
		response.setData("Deal de id "+ dealId + " apagada com sucesso");
		return ResponseEntity.ok().body(response);
	}
		
	


}
