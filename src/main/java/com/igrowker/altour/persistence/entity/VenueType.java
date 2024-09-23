package com.igrowker.altour.persistence.entity;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enum_venue_types")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class VenueType {
    // REPRESENTA LAS CATEGORIAS DE BUSQUEDA DEL USUARIO REPRESENTANDO LAS CATEGORIAS DE EnumVenueTypes =>  RESTAURANT, SHOPPING, FAST_FOOD....
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String venueType;
}
