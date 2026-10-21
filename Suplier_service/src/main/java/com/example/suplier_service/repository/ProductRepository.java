package com.example.suplier_service.repository;

import com.example.suplier_service.entitiy.ProductEntitiy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntitiy,String> {
}
