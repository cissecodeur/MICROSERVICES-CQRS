package com.sid.services;

import com.sid.entities.Produit;

import java.util.List;

public interface ProduitService {

  public List<Produit> getAllProduit();
  public Produit getProduitById(Long id);

}
