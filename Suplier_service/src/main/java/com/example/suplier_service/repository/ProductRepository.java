package com.example.suplier_service.repository;

import com.example.suplier_service.entitiy.ProductEntitiy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntitiy,String>
{
    List<ProductEntitiy> findAllByUsername(String username);
    Optional<ProductEntitiy> findById(String id);
}
