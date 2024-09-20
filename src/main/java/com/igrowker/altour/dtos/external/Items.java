package com.igrowker.altour.dtos.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Items {

	List<Item> items;

}
/*
EJEMPLO DE RESPUESTA COMPLETA
{
    "items": [
        {
            "title": "Colosseo (Coliseo)",
            "id": "here:pds:place:380sr2yk-1ad6e01f4dcf495080cddf487062482b",
            "language": "it",
            "resultType": "place",
            "address": {
                "label": "Colosseo, Piazza del Colosseo, 00184 Roma RM, Italia",
                "countryCode": "ITA",
                "countryName": "Italia",
                "state": "Lazio",
                "countyCode": "RM",
                "county": "Roma",
                "city": "Roma",
                "district": "Celio",
                "street": "Piazza del Colosseo",
                "postalCode": "00184"
            },
            "position": {
                "lat": 41.88989,
                "lng": 12.49337
            },
            "access": [
                {
                    "lat": 41.89022,
                    "lng": 12.49355
                }
            ],
            "distance": 6888773,
            "categories": [
                {
                    "id": "300-3000-0025",
                    "name": "Monumento storico",
                    "primary": true
                },
                {
                    "id": "300-3000-0000",
                    "name": "Punto di riferimento o attrazione"
                }
            ],
            "references": [
                {
                    "supplier": {
                        "id": "core"
                    },
                    "id": "50989397"
                },
                {
                    "supplier": {
                        "id": "tripadvisor"
                    },
                    "id": "192285"
                },
                {
                    "supplier": {
                        "id": "tripadvisor"
                    },
                    "id": "25381549"
                },
                {
                    "supplier": {
                        "id": "yelp"
                    },
                    "id": "P3SWgObRBp3PJCvDc2YH_A"
                },
                {
                    "supplier": {
                        "id": "yelp"
                    },
                    "id": "QhvsysQveiuOSoP9LeSz9A"
                },
                {
                    "supplier": {
                        "id": "yelp"
                    },
                    "id": "wMDtEfXqKTRXZBx1EzDNng"
                }
            ],
            "contacts": [
                {
                    "phone": [
                        {
                            "value": "+390639967700"
                        },
                        {
                            "value": "+390667232101",
                            "categories": [
                                {
                                    "id": "300-3000-0025"
                                }
                            ]
                        },
                        {
                            "value": "+39067004261"
                        },
                        {
                            "value": "+393491595278",
                            "categories": [
                                {
                                    "id": "300-3000-0025"
                                }
                            ]
                        },
                        {
                            "value": "+393906699841",
                            "categories": [
                                {
                                    "id": "300-3000-0000"
                                }
                            ]
                        }
                    ],
                    "www": [
                        {
                            "value": "https://www.thecolosseum.org",
                            "categories": [
                                {
                                    "id": "300-3000-0025"
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
}
 */