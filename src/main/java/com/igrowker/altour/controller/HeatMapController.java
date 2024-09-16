package com.igrowker.altour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.api.externalDtos.JamDataDTO;
import com.igrowker.altour.service.HeatMapService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HeatMapController {
	
 /* Devuelve datos en tiempo real sobre la
congestión turística en diferentes zonas de la ciudad*/
	
	@Autowired
	private HeatMapService heatMapService;

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	@GetMapping("/mapa-calor")
	public Mono<List<JamDataDTO>> mapaCalor(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam Integer rad) {

		return heatMapService.getJamDestinations(hereMapsApiKey ,lat, lng, rad);
	}
}
