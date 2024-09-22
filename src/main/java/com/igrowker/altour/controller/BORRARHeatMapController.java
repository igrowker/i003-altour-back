package com.igrowker.altour.controller;

import java.util.List;

import com.igrowker.altour.dtos.external.frontend.HeatMapPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igrowker.altour.dtos.external.frontend.JamDataDTO;
import com.igrowker.altour.service.BORRARHeatMapService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("borrar") // TODO definir si usaremos endpoints en españo como suguiere igrowker o endpoints en ingles
public class BORRARHeatMapController {
	
 /* Devuelve datos en tiempo real sobre la
congestión turística en diferentes zonas de la ciudad*/
	
	@Autowired
	private BORRARHeatMapService BORRARHeatMapService;

	@GetMapping("mapa-calor")
	public Mono<List<JamDataDTO>> mapaCalor(@RequestParam Double lat, @RequestParam Double lng,
			@RequestParam Integer rad) {

		return BORRARHeatMapService.getJamDestinations(lat, lng, rad);
	}

	@GetMapping("/heat-map")
	public ResponseEntity<List<HeatMapPoint>> getHeatMap(@RequestParam Double lat,
															@RequestParam Double lng,
															@RequestParam Integer rad){
		return new ResponseEntity<>(BORRARHeatMapService.getHeatMap(lat, lng, rad), HttpStatus.OK);
	}

}
