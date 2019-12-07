package com.cdpu.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BuyOptionDTO {

	private Long id;
	@NotNull(message = "Insira o id da oferta")
	private Long deal;
	
	@NotNull(message = "Informe um titulo")
	@Length(min = 5, message = "A titulo deve ter no mínimo 5 caracteres")
	private String title;
	
	@NotNull(message = "Informe o preço normal")
	private Double normalPrice;
	
	@NotNull(message = "Informe o percentual de desconto")	
	private Double percentageDiscount;
	
	@NotNull(message = "Informe a quantidade de cupons")	
	private Long quantityCupom;
	
	@NotNull(message = "Informe a data de inicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date startDate;
	
	@NotNull(message = "Informe a data final")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date endDate;
	
	private Long quantitySold;
}
