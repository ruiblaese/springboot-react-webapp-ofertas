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
import com.cdpu.dto.UserDTO;
import com.cdpu.entity.Deal;
import com.cdpu.entity.User;
import com.cdpu.response.Response;
import com.cdpu.service.UserService;
import com.cdpu.util.Bcrypt;
import com.cdpu.util.ConvertEntities;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO dto, 
			BindingResult result){
		
		Response<UserDTO> response = new Response<UserDTO>();
		
		if (result.hasErrors()) {
			System.out.println(result);
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		User user = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(user));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping()
	public ResponseEntity<Response<List<UserDTO>>> findAll(){
		
		ArrayList<User> list = (ArrayList<User>) service.findAll();
		
		List<UserDTO> dto = new ArrayList<>();
		list.forEach(i -> dto.add(ConvertEntities.convertUserToUserDto(i)));
		
		Response<List<UserDTO>> response = new Response<List<UserDTO>>();
		response.setData(dto);		
		
		return ResponseEntity.ok().body(response);
		
	}		
	
	
	private User convertDtoToEntity(UserDTO dto) {
		User u = new User();
		u.setId(dto.getId());
		u.setEmail(dto.getEmail());
		u.setName(dto.getName());
		u.setPassword(Bcrypt.getHash(dto.getPassword()));
		
		return u;
	}
	
	private UserDTO convertEntityToDto(User u) {
		UserDTO dto = new UserDTO();
		dto.setId(u.getId());
		dto.setEmail(u.getEmail());
		dto.setName(u.getName());
		//dto.setPassword(u.getPassword());
		
		return dto;
	}
	
	

}
