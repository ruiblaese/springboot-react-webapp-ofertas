package com.cdpu.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cdpu.dto.DealDTO;
import com.cdpu.entity.Deal;
import com.cdpu.service.DealService;
import com.cdpu.util.ConvertEntities;
import com.cdpu.util.enums.TypeDeal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DealControllerTest {
	
	private static final String URL_POST = "/deal";

	private static final Long ID = 1L;
	private static final Date DATE = new Date();
	private static final LocalDate TODAY = LocalDate.now();
	private static final String TITLE = "oferta teste";
	private static final String TEXT = "texto oferta teste";
	private static final Long TOTAL_SOLD = 1L;
	private static final TypeDeal TYPE = TypeDeal.PRODUTO;
	private static final String URL = "/oferta/" + ID;
	
	@MockBean
	DealService service;
	
	@Autowired
	MockMvc mvc;	
	
	@Test
	@WithMockUser
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Deal.class))).willReturn(getMockWalletItem());
		
		mvc.perform(MockMvcRequestBuilders.post(URL_POST).content(getJsonPayload())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.createDate").isNotEmpty())
		.andExpect(jsonPath("$.data.publishDate").isNotEmpty())
		.andExpect(jsonPath("$.data.endDate").value(TODAY.format(getDateFormater())))		
		.andExpect(jsonPath("$.data.title").value(TITLE))
		.andExpect(jsonPath("$.data.text").value(TEXT))
		.andExpect(jsonPath("$.data.totalSold").value(TOTAL_SOLD))		
		.andExpect(jsonPath("$.data.type").value(TYPE.toString()))
		.andExpect(jsonPath("$.data.url").value(URL));
		
	}
		
	
	private Deal getMockWalletItem() {
		Deal deal = new Deal();
		deal.setId(ID);
		deal.setCreateDate(DATE);
		deal.setPublishDate(DATE);
		deal.setEndDate(DATE);
		deal.setTitle(TITLE);
		deal.setText(TEXT);
		deal.setTotalSold(TOTAL_SOLD);
		deal.setType(TYPE);
		deal.setUrl(URL);		
				
		return deal;
	}
	
	public String getJsonPayload() throws JsonProcessingException {
		DealDTO dto = ConvertEntities.convertDealToDealDto(getMockWalletItem()); 

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
	private DateTimeFormatter getDateFormater() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return formatter;
	}	

}
