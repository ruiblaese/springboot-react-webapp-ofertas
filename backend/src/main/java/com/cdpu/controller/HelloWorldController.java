package com.cdpu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.cdpu.response.Response;


@RestController
@RequestMapping("")
public class HelloWorldController {

	@RequestMapping("")
	void handleRedirect(HttpServletResponse response) throws IOException {
	  response.sendRedirect("https://frontend-api-ofertas.netlify.com");
	}
	
}
