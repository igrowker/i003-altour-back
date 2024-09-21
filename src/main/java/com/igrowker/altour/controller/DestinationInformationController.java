package com.igrowker.altour.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.api.externalDtos.BORRAR.Item;
import com.igrowker.altour.service.DestinationInformationServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "") // TODO definir si usaremos endpoints en españo como suguiere igrowker o endpoints en ingles
public class DestinationInformationController {

	/*
	 * Devuelve información detallada de un punto de interés turístico específico
	 */
	@Autowired
	private DestinationInformationServiceImpl destinationInformationService;

	@GetMapping("/destinos/info")
	public Mono<Item> getDestinationInfo(@RequestParam String placeId) {
		return destinationInformationService.getDetailedDestinationInfo(placeId);
	}

}
