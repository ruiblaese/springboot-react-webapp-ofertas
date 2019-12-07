package com.cdpu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cdpu.util.enums.TypeDeal;

import lombok.Data;

@Entity
@Data
public class Deal implements Serializable{
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -3936753947346905332L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	@Column(nullable = false)	
	private String title;
	
	@Column(nullable = false)
	private String text;
	
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@Column(name = "publish_date", nullable = false)
	private Date publishDate;
	
	@Column(name = "end_date", nullable = false)
	private Date endDate;
		
	@Column(name = "total_sold",nullable = false)
	private Long totalSold;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private TypeDeal type;
	
	@Column(nullable = true)
	private String url;
	
}
