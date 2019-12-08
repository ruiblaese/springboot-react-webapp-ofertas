package com.cdpu.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import com.cdpu.util.enums.TypeDeal;

@Data
public class DealDTO {

	private Long id;
	
	@NotNull(message = "Informe um titulo")
	@Length(min = 5, message = "A titulo deve ter no mínimo 5 caracteres")
	private String title;
	
	@NotNull(message = "Informe um texto de destaque")
	@Length(min = 5, message = "O texto de destaque deve ter no mínimo 5 caracteres")
	private String text;	
	
	@NotNull(message = "Informe a data de publicação")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date publishDate;
		
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp="^(LOCAL|PRODUTO|VIAGEM)$", message = "Para o tipo somente são aceitos os valores LOCAL, PRODUTO, VIAGEM")
	private String type;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")	
	private Date endDate;	
	
	@DecimalMin(value = "0", inclusive = true, message = "Validade minima é 0 dias(0 = um dia)")
	@NotNull(message = "Informe a validade da oferta")
	private Long validity;
	
	private String url;
	private Long totalSold;
	private Date createDate;
	
	
}
