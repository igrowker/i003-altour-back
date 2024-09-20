package com.igrowker.altour.controller;


import java.util.List;

import com.igrowker.altour.dtos.external.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.igrowker.altour.service.DestinationFilterService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "destinos/")
public class DestinationFilterController {

	@Autowired
	private DestinationFilterService destinationFilterService;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	@GetMapping("filtrar")
	public Mono<List<Item>> filter(@RequestParam Double lat,
								   @RequestParam Double lng,
								   @RequestParam Integer rad,
								   @RequestParam String activity) {

		return destinationFilterService.getDestinations(lat, lng, rad, activity, hereMapsApiKey);
	}

}
