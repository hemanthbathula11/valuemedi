package com.example.valuemedi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	String code;
	String name;
	String batch;
	Integer stock;
	Integer deal;
	Integer free;
	Double mrp;
	Double rate;
	Date exp;
	String company;
	String supplier;

}
