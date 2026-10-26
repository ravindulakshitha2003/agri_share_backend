package com.example.suplier_service.repository;

import com.example.suplier_service.entitiy.PurchesEntitiy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PruchesRepository extends MongoRepository<PurchesEntitiy,String> {
}
