package com.uk.elasticsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import com.uk.elasticsearch.model.House;
import com.uk.elasticsearch.repo.HouseRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ElasticsearchApplication {
	
	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}
	
	@Autowired
	private ElasticsearchOperations elasticSearchOperations;
	
	@Autowired
	private HouseRepo houseRepo;
	
	@PreDestroy
	public void deleteIndex() {
		elasticSearchOperations.indexOps(House.class).delete();
	}
	
	@PostConstruct
	public void buildIndex() {

		elasticSearchOperations.indexOps(House.class).refresh();
		houseRepo.deleteAll();
		houseRepo.saveAll(prepareData());
	}
	
	private List<House> prepareData() {
		Resource resource = new ClassPathResource("London.csv");
		List<House> houseList = new ArrayList<House>();
		int lineNo = 0;
		try (
			InputStream input = resource.getInputStream();
			Scanner scanner = new Scanner(resource.getInputStream());) {
			
			while (scanner.hasNextLine()) {
				++lineNo;				
				String line = scanner.nextLine();
				if(lineNo == 1) continue;
				Optional<House> product = 
						csvToHouseMapper(line);
				if(product.isPresent())
					houseList.add(product.get());
			}
		} catch (Exception e) {
			log.error("LineNo : {} File read error {}",lineNo, e);;
		}
		return houseList;
	}
	
	private Optional<House> csvToHouseMapper(final String line) {
		try (			
			Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				String id = rowScanner.next();
				String propertyName = rowScanner.next();
				String price = rowScanner.next();
				String houseType = rowScanner.next();
				String area = rowScanner.next();
				String numOfBedrooms = rowScanner.next();
				String numOfBathrooms = rowScanner.next();
				String numOfReceptions = rowScanner.next();
				String location = rowScanner.next();
				String cityCountry = rowScanner.next();
				String postalCode = rowScanner.next();
				
				return Optional.of(
						House.builder()
						.id(id)
						.propertyName(propertyName)
						.price(Double.valueOf(price))
						.houseType(houseType)
						.area(Double.valueOf(area))
						.numOfBedrooms(Integer.valueOf(numOfBedrooms))
						.numOfBathrooms(Integer.valueOf(numOfBathrooms))
						.numOfReceptions(Integer.valueOf(numOfReceptions))
						.location(location)
						.cityCountry(cityCountry)
						.postalCode(postalCode)
						.build());

			}
		}
		return Optional.of(null);
	}

}
