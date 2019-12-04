package com.cdpu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdpu.dto.DealDTO;
import com.cdpu.response.Response;
import com.cdpu.service.DealService;

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
		/*
		Deal Deal = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(Deal));
		*/
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);		
	}
	
	@GetMapping
	public ResponseEntity<Response<List<DealDTO>>> findAll(){
				
		Response<List<DealDTO>> response = new Response<List<DealDTO>>();
		response.setData(new ArrayList<DealDTO>());		
		
		return ResponseEntity.ok().body(response);
		
	}

}
