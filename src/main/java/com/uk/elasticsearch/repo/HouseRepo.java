package com.uk.elasticsearch.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.uk.elasticsearch.model.House;


@Repository
public interface HouseRepo extends ElasticsearchRepository<House, String>{
	
	List<House> findByPropertyName(String propertyName);
}
