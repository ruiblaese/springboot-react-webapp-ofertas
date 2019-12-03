package com.cdpu.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
		
	private Long id;
	@Length(min=6, message="A senha deve conter no minimo 6 caracteres")
	private String password;
	@Length(min=3, max=50, message="O nome deve conter entre 3 e 40 caracteres")
	private String name;
	@Email(message="Email inválido")
	private String email;

}
