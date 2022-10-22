package com.example.valuemedi.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.valuemedi.entities.InventoryItem;

@Repository
public interface InventoryItemRepository extends PagingAndSortingRepository<InventoryItem, Long> {
	
	List<InventoryItem> findByCode(String code);
	List<InventoryItem> findByCompany(String company);
	List<InventoryItem> findByName(String name);
}
