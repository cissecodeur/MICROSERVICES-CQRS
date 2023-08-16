package com.sid.dtos;

import com.sid.entities.Produit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProduitEvent {

    private String eventType;
    private Produit produit;
}
