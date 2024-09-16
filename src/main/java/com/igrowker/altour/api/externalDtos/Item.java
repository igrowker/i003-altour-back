package com.igrowker.altour.api.externalDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
	private String title;
	private Long distance;
	private Position position;
	private Address address;
	private List<Contact> contacts;
	/*
	private String description;
	private Double price;
	private String openingHours;
	private DestinationType destinationType;
	 */
}