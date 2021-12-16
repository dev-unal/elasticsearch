package com.uk.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uk.elasticsearch.exception.HouseNotFoundException;
import com.uk.elasticsearch.model.House;
import com.uk.elasticsearch.repo.HouseRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HouseService {

	private static final String HOUSE_INDEX = "house-index";

	private ElasticsearchOperations elasticsearchOperations;

	private HouseRepo houseRepo;

	@Autowired
	public HouseService(HouseRepo houseRepo, ElasticsearchOperations elasticsearchOperations) {
		super();
		this.houseRepo = houseRepo;
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public House createNewHouse(final House house) {
		return houseRepo.save(house);
	}

	public House getHouseById(String id) throws HouseNotFoundException{
		Optional<House> house = houseRepo.findById(id);
		if(house.isEmpty()) {
			 throw new HouseNotFoundException("Entity not found");
		}
		return house.get();
	}

	@Transactional
	public House updateHouse(String id, House house) {
		Optional<House> houseFromDB = houseRepo.findById(id);
		
		House houseToUpdate = houseFromDB.get();
		
		houseToUpdate.setPropertyName(house.getPropertyName());
		houseToUpdate.setPrice(house.getPrice());
		houseToUpdate.setHouseType(house.getHouseType());
		houseToUpdate.setNumOfBedrooms(house.getNumOfBedrooms());
		houseToUpdate.setNumOfBathrooms(house.getNumOfBathrooms());
		houseToUpdate.setNumOfReceptions(house.getNumOfReceptions());
		houseToUpdate.setLocation(house.getLocation());
		houseToUpdate.setCityCountry(house.getCityCountry());
		houseToUpdate.setPostalCode(house.getPostalCode());
		
		return houseRepo.save(houseToUpdate);
		
	}
	
	public void deleteHouseById(String id) {
		houseRepo.deleteById(id);
	}

	public List<House> findByHousePropertyName(final String propertyName) {
		return houseRepo.findByPropertyName(propertyName);
	}

	public List<House> findByHousePrice(final Double min, final Double max) {
		Criteria criteria = new Criteria("price").greaterThan(min).lessThan(max);
		Query searchQuery = new CriteriaQuery(criteria);

		SearchHits<House> houseHits = elasticsearchOperations.search(searchQuery, House.class,
				IndexCoordinates.of(HOUSE_INDEX));

		List<House> houseMatches = new ArrayList<House>();
		houseHits.forEach(srchHit -> {
			houseMatches.add(srchHit.getContent());
		});
		return houseMatches;
	}

	public List<House> searchInAll(final String query) {
		log.info("Search with query {}", query);

		// 1. Create query on multiple fields enabling fuzzy search
		QueryBuilder queryBuilder = QueryBuilders
				.multiMatchQuery(query, "propertyName", "houseType", "cityCountry", "location")
				.fuzziness(Fuzziness.AUTO);

		Query searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder).build();

		// 2. Execute search
		SearchHits<House> houseHits = elasticsearchOperations.search(searchQuery, House.class,
				IndexCoordinates.of(HOUSE_INDEX));

		// 3. Map searchHits to product list
		List<House> houseMatches = new ArrayList<House>();
		houseHits.forEach(srchHit -> {
			houseMatches.add(srchHit.getContent());
		});
		return houseMatches;
	}

}
