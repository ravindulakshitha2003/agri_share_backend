package com.example.suplier_service.service;


import com.example.suplier_service.Mapper.ProductDTOEntity;
import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private  final ProductRepository productRepository;
    private  ProductDTOEntity productDTOEntityl;

    public ProductService(ProductRepository productRepository, ProductDTOEntity productDTOEntityl) {
        this.productRepository = productRepository;
        this.productDTOEntityl = productDTOEntityl;
    }

    public ResponseEntity<ProductEntitiy> saveGoods(ProductRequestDTO dto){
          ProductEntitiy productEntitiy  =  productRepository.save(productDTOEntityl.DtoTOEntity(dto));

          if(productEntitiy.getId()==null){
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
          return  new ResponseEntity<>(productEntitiy,HttpStatus.OK);
    }
}


