package com.sid.services;

import com.sid.Repositories.ProduitRepository;
import com.sid.dtos.ProduitEvent;
import com.sid.entities.Produit;
import com.sid.exceptions.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public Produit creerProduit(ProduitEvent produitEvent){
        String KAFKA_PRODUCT_TOPIC = "product-cqrs-topic";
        String KAFKA_CREATE_EVENT = "create-event";
        Produit produit = produitRepository.save(produitEvent.getProduit());

        ProduitEvent prodEvent = ProduitEvent.builder()
                                             .produit(produit)
                                             .eventType(KAFKA_CREATE_EVENT)
                                             .build();

        kafkaTemplate.send(KAFKA_PRODUCT_TOPIC,prodEvent);
        return produit;
    }

    public Produit modifierProduit(Long id,ProduitEvent produitEvent){
        String KAFKA_PRODUCT_TOPIC = "product-cqrs-topic";
        String KAFKA_UPDATE_EVENT = "update-event";

        Produit produitExistant = produitRepository.findById(id)
                                                   .orElseThrow(() -> new CustomException("Ce produit est introuvable", HttpStatus.NOT_FOUND));
        produitExistant.setDescription(produitEvent.getProduit().getDescription());
        produitExistant.setNom(produitEvent.getProduit().getNom());
        produitExistant.setPrix(produitEvent.getProduit().getPrix());
        produitRepository.save(produitExistant);

        ProduitEvent produitEvent1 = ProduitEvent.builder()
                                                 .produit(produitExistant)
                                                 .eventType(KAFKA_UPDATE_EVENT)
                                                 .build();

        kafkaTemplate.send(KAFKA_PRODUCT_TOPIC,produitEvent1);

        return produitExistant;
    }



}
