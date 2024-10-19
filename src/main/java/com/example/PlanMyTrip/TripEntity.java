package com.example.PlanMyTrip;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TripEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tripId;
	
	@Column
	private String userName;
	
	@Column
	private String contact;
	
	@Column
	private String start;
	
	@Column
	private String destination;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date depart;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returndate;
	
	@Column
	private Double amount;
	
	


}
