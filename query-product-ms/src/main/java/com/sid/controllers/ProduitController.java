package com.sid.controllers;


import com.sid.services.ProduitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/produits")
@Slf4j
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduit(){
        log.info("--- getAllProduit produit ---" );
        return new ResponseEntity<>(produitService.getAllProduit(), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getProduitById(@PathVariable Long id){
        log.info("--- getAllProduit produit ---" );
        return new ResponseEntity<>(produitService.getProduitById(id),HttpStatus.OK);
    }
}
