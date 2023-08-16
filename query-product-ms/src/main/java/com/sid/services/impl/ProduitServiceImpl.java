package com.sid.services.impl;

import com.sid.entities.Produit;
import com.sid.exceptions.CustomException;
import com.sid.dtos.ProduitEvent;
import com.sid.repositories.ProduitRepository;
import com.sid.services.ProduitEventProcessingService;
import com.sid.services.ProduitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ProduitServiceImpl implements ProduitService, ProduitEventProcessingService {

    static String KAFKA_CREATE_EVENT = "create-event";
    static String KAFKA_UPDATE_EVENT = "update-event";

    private final ProduitRepository produitRepository;

    @Override
    public List<Produit> getAllProduit() {
        return produitRepository.findAll();
    }

    @Override
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                                .orElseThrow(()-> new CustomException("Ce produit est introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    @KafkaListener(topics = "product-cqrs-topic",groupId = "new-topic-cqrs-event-GID")
    public void produitEventProcessing(ProduitEvent produitEvent) {

        Produit produit = produitEvent.getProduit();

        if (produitEvent.getEventType().equalsIgnoreCase(KAFKA_CREATE_EVENT)){
           produitRepository.save(produit);
        }

        else if (produitEvent.getEventType().equalsIgnoreCase(KAFKA_UPDATE_EVENT)){
            Produit produitExistant =  produitRepository.findById(produit.getId())
                            .orElseThrow(()->new CustomException("Produit introuvable",HttpStatus.NOT_FOUND));

            produitExistant.setDescription(produit.getDescription());
            produitExistant.setNom(produit.getNom());
            produitExistant.setPrix(produit.getPrix());

            produitRepository.save(produitExistant);
        }
    }
}
