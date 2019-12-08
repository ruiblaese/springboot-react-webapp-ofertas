package com.cdpu.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cdpu.entity.Deal;
import com.cdpu.util.enums.TypeDeal;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DealRepositoryTest {

	private static final Date DATE = new Date();
	
	@Autowired
	DealRepository repository;

	@Before
	public void setUp() {
		LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date currentDateMinusOneDay = Date.from(localDateTime.minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
		Date currentDatePlusOneDay = Date.from(localDateTime.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
		
		Deal dealMinusOneDay = new Deal();
		dealMinusOneDay.setCreateDate(currentDateMinusOneDay);
		dealMinusOneDay.setPublishDate(currentDateMinusOneDay);
		dealMinusOneDay.setEndDate(currentDateMinusOneDay);
		dealMinusOneDay.setText("Oferta Vencida");
		dealMinusOneDay.setTitle("Oferta Vencida");
		dealMinusOneDay.setTotalSold(0L);
		dealMinusOneDay.setType(TypeDeal.PRODUTO);
		dealMinusOneDay.setUrl("");
		repository.save(dealMinusOneDay);
		
		Deal dealPlusOneDay = new Deal();
		dealPlusOneDay.setCreateDate(currentDatePlusOneDay);
		dealPlusOneDay.setPublishDate(currentDatePlusOneDay);
		dealPlusOneDay.setEndDate(currentDatePlusOneDay);
		dealPlusOneDay.setText("Oferta não lancada");
		dealPlusOneDay.setTitle("Oferta não lancada");
		dealPlusOneDay.setTotalSold(0L);
		dealPlusOneDay.setType(TypeDeal.PRODUTO);
		dealPlusOneDay.setUrl("");
		repository.save(dealPlusOneDay);
		
		Deal deal = new Deal();
		deal.setCreateDate(DATE);
		deal.setPublishDate(DATE);
		deal.setEndDate(DATE);
		deal.setText("Oferta do dia");
		deal.setTitle("Oferta do dia");
		deal.setTotalSold(0L);
		deal.setType(TypeDeal.PRODUTO);
		deal.setUrl("");
		repository.save(deal);
		
		Deal deal2 = new Deal();
		deal2.setCreateDate(DATE);
		deal2.setPublishDate(currentDateMinusOneDay);
		deal2.setEndDate(currentDatePlusOneDay);
		deal2.setText("Oferta de ontem ate amanha");
		deal2.setTitle("Oferta de ontem ate amanha");
		deal2.setTotalSold(0L);
		deal2.setType(TypeDeal.PRODUTO);
		deal2.setUrl("");
		repository.save(deal2);
		
		
	}

	@After
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void testSave() {
		Deal deal = new Deal();
		deal.setCreateDate(new Date());
		deal.setPublishDate(deal.getCreateDate());
		deal.setEndDate(deal.getCreateDate());
		deal.setText("Oferta 1");
		deal.setTitle("Oferta 1 titulo");
		deal.setTotalSold(0L);
		deal.setType(TypeDeal.PRODUTO);
		deal.setUrl("");
		
		Deal response = repository.save(deal);		
		assertNotNull(response);
	}

	
	@Test
	public void testFindActiveDeals() {
		List<Deal> response = repository.findByPublishDateLessThanEqualAndEndDateGreaterThanEqualOrderById(DATE,DATE);

		assertEquals(response.size(), 2);
	}
	
}
