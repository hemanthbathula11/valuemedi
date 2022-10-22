package com.example.valuemedi.api;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.valuemedi.entities.InventoryItem;
import com.example.valuemedi.repository.InventoryItemRepository;

import net.bytebuddy.asm.Advice.Return;

@RequestMapping("/api")
@RestController
public class InventoryAPI {
	
	InventoryItemRepository ir;
	
	public InventoryAPI(InventoryItemRepository ir) {
		this.ir = ir;
	}
	
	@GetMapping
	public Iterable<InventoryItem> get(@RequestParam(defaultValue = "1") Integer page) {
		System.out.println(page);
		if (page != -1) {
			Integer start = (page - 1) * 10;
			Integer end = start + 10;
			//System.out.println(page - 1, 10);
			PageRequest request =  PageRequest.of(page - 1, 10);
			return this.ir.findAll(request);
		}
		else {
			return this.ir.findAll();
		}
	}
	
	@GetMapping("/search")
	public Iterable<InventoryItem> search(@RequestParam("q") String term, @RequestParam(defaultValue = "false") String expiry){
		
		ArrayList<InventoryItem> filtered = new ArrayList<>();
		this.ir.findAll().forEach(Item -> {
			
			if (Item.getName().contains(term.toUpperCase())) {
				if(expiry.equals("true")) {
					if (!Item.getExp().before(new Date())) {
						filtered.add(Item);
					}
				}
				else {
				filtered.add(Item);
				}
			}
			
		});
		
		return filtered;
		
	}
	
	@GetMapping("/filter")
	public Iterable<InventoryItem> getbycode(@RequestParam("code") String code){
		
		ArrayList<InventoryItem> filtered = new ArrayList<>();
		this.ir.findByCode(code).forEach(item -> {
			
			if (item.getStock() > 0) {
				filtered.add(item);
			}
		}
		);
		return filtered;
		
	}
	
	@PostMapping
	public void post(@RequestParam MultipartFile data) throws Exception {		
		System.out.println(data.getSize());
		
	    String stringData = new String(data.getBytes());
	    String[] lines = stringData.split("\n");
	    
	    for(int i = 1; i < lines.length; i++) {
	    	
	    	String[] values = lines[i].split(",");
	    	
	    	if (values.length == 11) {
	    		
	    		Date dt = new Date();
	    		try {
	    			dt = new SimpleDateFormat("dd/MM/yyyy").parse(values[8]);
	    		}
	    		catch(Exception e) {
	    			

	    			
	    		}
	    		

	    		InventoryItem item = new InventoryItem(null, values[0],
	    				values[1],
	    				values[2],
	    				Integer.parseInt(values[3]),
	    				Integer.parseInt(values[4]),
	    				Integer.parseInt(values[5]),
	    				Double.parseDouble(values[6]),
	    				Double.parseDouble(values[7]),
	    				dt,
	    				values[9],
	    				values[10]
	    		);
	    		
	    		System.out.println(item);
	    		this.ir.save(item);
	    		
	    	}
	    	
	    	
	    	
	    }
	}

}
