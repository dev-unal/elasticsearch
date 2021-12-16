package com.uk.elasticsearch.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uk.elasticsearch.exception.HouseNotFoundException;
import com.uk.elasticsearch.model.House;
import com.uk.elasticsearch.service.HouseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/house")
@Slf4j
public class HouseController {

	private HouseService houseService;

	@Autowired
	public HouseController(HouseService houseService) {
		this.houseService = houseService;
	}

	@PostMapping
	public ResponseEntity<House> createNewHouse(@RequestBody House house) {
		if(house == null) {
			throw new IllegalArgumentException("house is invalid");
		}
		House createdHouse = houseService.createNewHouse(house);
		return new ResponseEntity<House>(createdHouse, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<House> getHouseById(@PathVariable("id") String id) throws HouseNotFoundException{
		if(id == null) {
			throw new IllegalArgumentException("id is invalid");
		}
		House house = houseService.getHouseById(id);

		return ResponseEntity.ok(house);
	}

	@PutMapping("/{id}")
	public ResponseEntity<House> updateHouse(@PathVariable("id") String id, @RequestBody House house) {
		if(id == null || house == null) {
			throw new IllegalArgumentException("id or house is invalid");
		}
		return ResponseEntity.ok(houseService.updateHouse(id, house));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHouse(@PathVariable("id") String id) {
		if(id == null) {
			throw new IllegalArgumentException("id is invalid");
		}
		houseService.deleteHouseById(id);
		return ResponseEntity.ok().build();
	}				

	@GetMapping("/search")
	@ResponseBody
	public List<House> searchInAll(@RequestParam String query) {
		log.info("Search Query : {}", query);
		if(query == null) {
			throw new IllegalArgumentException("query param is invalid");
		}
		List<House> houseList = houseService.searchInAll(query);
		log.info("Search Result : {}", houseList);
		return houseList;
	}
	
	@GetMapping("/search/price")
	@ResponseBody
	public List<House> searchByPrice(@RequestParam Double min, @RequestParam Double max) {
		log.info("Search Params Min : {} Max : {}", min, max);
		if(min == null || max == null) {
			throw new IllegalArgumentException("min or max param is invalid");
		}
		List<House> houseList = houseService.findByHousePrice(min, max);
		log.info("Search Result : {}", houseList);
		return houseList;
	}

}
