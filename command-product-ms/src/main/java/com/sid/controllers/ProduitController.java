package com.sid.controllers;

import com.sid.dtos.ProduitEvent;
import com.sid.services.ProduitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping("/creer")
    ResponseEntity<?> creerProduit(@RequestBody ProduitEvent produitEvent){
        log.info("--- Creation produit {} ---" + produitEvent.getProduit().getNom());
        return new ResponseEntity<>(produitService.creerProduit(produitEvent), HttpStatus.CREATED) ;

    }

    @PutMapping("/modifier/{id}")
    ResponseEntity<?> modifierProduit(@PathVariable Long id, @RequestBody ProduitEvent produitEvent){
        log.info("--- Modification produit {} ---" + produitEvent.getProduit().getNom());
        return new ResponseEntity<>(produitService.modifierProduit(id,produitEvent), HttpStatus.OK) ;

    }
}
