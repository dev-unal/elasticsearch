package com.uk.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "house-index")
public class House {
	
	@Id
	private String id;
	
	@Field(type = FieldType.Text, name = "propertyName")
	private String propertyName;
	
	@Field(type = FieldType.Double, name = "price")
	private Double price;
	
	@Field(type = FieldType.Text, name = "houseType")
	private String houseType;
	
	@Field(type = FieldType.Double, name = "area")
	private Double area;
	
	@Field(type = FieldType.Integer, name = "numOfBedrooms")
	private Integer numOfBedrooms;
	
	@Field(type = FieldType.Integer, name = "numOfBathrooms")
	private Integer numOfBathrooms;	

	@Field(type = FieldType.Integer, name = "numOfReceptions")
	private Integer numOfReceptions;
	
	@Field(type = FieldType.Text, name = "location")
	private String location;
	
	@Field(type = FieldType.Keyword, name = "cityCountry")
	private String cityCountry;
	
	@Field(type = FieldType.Keyword, name = "postalCode")
	private String postalCode;
}
