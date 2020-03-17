package com.cdpu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdpu.response.Response;


@RestController
@RequestMapping("")
public class HelloWorldController {

	@GetMapping
	public ResponseEntity<Response<String>> hello(){
			
		Response<String> response = new Response<String>();
		response.setData("hello world");
		
		return ResponseEntity.ok().body(response);
	}
	
}
