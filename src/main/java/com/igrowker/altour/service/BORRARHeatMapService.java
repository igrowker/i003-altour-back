package com.igrowker.altour.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.igrowker.altour.dtos.external.Results;
import com.igrowker.altour.dtos.external.frontend.GoogleMapsPoint;
import com.igrowker.altour.dtos.external.frontend.HeatMapPoint;
import com.igrowker.altour.dtos.external.frontend.JamDataDTO;
import com.igrowker.altour.dtos.external.hereMaps.HereMapResult;
import com.igrowker.altour.dtos.external.hereMaps.HereMapsResults;
import com.igrowker.altour.dtos.external.hereMaps.Link;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class BORRARHeatMapService {
	private final RestTemplate restTemplate;

	private final WebClient webClient;

	private final String hereMapsBaseUrl = "https://data.traffic.hereapi.com/v7/";

	@Value("${here_maps.api.key}")
	private String hereMapsApiKey;

	public BORRARHeatMapService(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.baseUrl(hereMapsBaseUrl).build();
	}

	public Mono<List<JamDataDTO>> getJamDestinations(Double lat, Double lng, Integer rad) {
		String uri = String.format("flow?apiKey=%s&in=circle:%s,%s;r=%d&q=all&locationReferencing=shape", hereMapsApiKey, lat, lng, rad);

		return webClient
				.get().uri(
						uri)
				.retrieve().bodyToMono(Results.class)
				.map(results -> results.getResults().stream()
						.map(result -> new JamDataDTO(result.getLocation().getDescription(),
								result.getCurrentFlow().getJamFactor()))
						.collect(Collectors.toList()));
	}

	// TODO MEJORAR METODO!
	public List<HeatMapPoint> getHeatMap(Double lat, Double lng, Integer rad){
		List<HeatMapPoint> heatMapPoints =new ArrayList<>();
		String uri = String.format("flow?apiKey=%s&in=circle:%s,%s;r=%d&q=all&locationReferencing=shape", hereMapsApiKey, lat, lng, rad);
		ResponseEntity<HereMapsResults> hereMapsResults = restTemplate.exchange(
				hereMapsBaseUrl + uri,
				HttpMethod.GET,
				new HttpEntity<>(new HttpHeaders()),
				HereMapsResults.class
		);

		// TODO hereMapsResults => Contiene las "calles completas", lista de lineas que representan las calees
		// TODO HereMapResult => es la calle, como una linea, con un unico valor de jam, y esta formada por muchas lineas o conectores
		// TODO links => representan los conectores o segmentos que forman cada calle
		// TODO points => representa cada punto que forma un segmento de calle

		if (hereMapsResults.getBody().getResults() !=null){
			for (int i = 0; i<hereMapsResults.getBody().getResults().size(); i++){
				HereMapResult result = hereMapsResults.getBody().getResults().get(i);
				List<Link> links = result.getLocation().getShape().getLinks();

				// TODO VERIFICAR=> Esto es porque here maps devuelve un jam de 0 a 10? y front pide de 0 a 100
				int weight = (int) (result.getCurrentFlow().getJamFactor()*10);

				for(int k =0; k< links.size(); k++){
					List<GoogleMapsPoint> points = links.get(k).getPoints();
					for( int l = 0; l<points.size(); l++){
						GoogleMapsPoint point = new GoogleMapsPoint(points.get(l).getLatitude() , points.get(l).getLonguitude());
						HeatMapPoint heatPoint = new HeatMapPoint(point, weight);
						heatMapPoints.add(heatPoint);
					}
				}

			}
		}
		return heatMapPoints;

	}
}
