package com.cdpu.service;

import static org.junit.Assert.assertNotNull;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cdpu.entity.Deal;
import com.cdpu.repository.DealRepository;
import com.cdpu.service.DealService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Service
public class DealServiceTest {
		
	@MockBean
	DealRepository repository;
	
	@Autowired
	DealService service;
	
	@Test
	public void testfindAllActiveDeals() {
	
		List<Deal> list = service.findAllActive();		
		assertNotNull(list);
	}
}
