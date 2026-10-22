package com.example.suplier_service.service;


import com.example.suplier_service.Mapper.ProductDTOEntity;
import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private  final ProductRepository productRepository;
    private  ProductDTOEntity productDTOEntityl;


    public ProductService(ProductRepository productRepository, ProductDTOEntity productDTOEntityl) {
        this.productRepository = productRepository;
        this.productDTOEntityl = productDTOEntityl;

    }

    public ResponseEntity<ProductEntitiy> saveGoods(ProductRequestDTO dto,String username){



          ProductEntitiy productEntitiy  =  productRepository.save(productDTOEntityl.DtoTOEntity(dto,username));

          if(productEntitiy.getId()==null){
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
          return  new ResponseEntity<>(productEntitiy,HttpStatus.OK);
    }

    public ResponseEntity<List<ProductEntitiy>> getall() {

        List<ProductEntitiy> data = productRepository.findAll();

        return ResponseEntity.ok(data);
    }

    public ResponseEntity<List<ProductEntitiy>> getallbyusername(
            String username) {

        List<ProductEntitiy> data =
                productRepository.findAllByUsername(username);

        if (data.isEmpty()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(data);
    }
}


