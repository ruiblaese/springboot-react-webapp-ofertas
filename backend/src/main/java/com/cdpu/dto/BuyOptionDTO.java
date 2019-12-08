package com.cdpu.dto;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
	@DecimalMin(value = "1.00", inclusive = true, message = "Preço de venda minimo deve ser 1.00")
	private Double normalPrice;
	
	@NotNull(message = "Informe o percentual de desconto")
	@DecimalMin(value = "0.01", inclusive = true, message = "Percentual minimo 0.01")
	@DecimalMax(value = "99.99", inclusive = true , message = "Percentual minimo 99.99")
	private Double percentageDiscount;

	@NotNull(message = "Informe o preço de venda")		
	private Double salePrice;
	
	@NotNull(message = "Informe a quantidade de cupons")
	@DecimalMin(value = "1", inclusive = true, message = "Quantidade minimo de cupons é 1")
	private Long quantityCupom;
	
	@NotNull(message = "Informe a data de inicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date startDate;
	
	@NotNull(message = "Informe a data final")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date endDate;
	
	private Long quantitySold;
}
