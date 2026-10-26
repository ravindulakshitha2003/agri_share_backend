package com.example.suplier_service.service;


import com.example.suplier_service.Mapper.ProductDTOEntity;
import com.example.suplier_service.dto.AuctionRequestDTO;
import com.example.suplier_service.dto.AuctionResponseDTO;
import com.example.suplier_service.dto.CreateAuctionRequestDTO;
import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private  final ProductRepository productRepository;
    private  ProductDTOEntity productDTOEntityl;
    private final WebClient auctionWebcliant;


    public ProductService(ProductRepository productRepository, ProductDTOEntity productDTOEntityl, WebClient auctionWebcliant) {
        this.productRepository = productRepository;
        this.productDTOEntityl = productDTOEntityl;

        this.auctionWebcliant = auctionWebcliant;
    }

    public ResponseEntity<ProductEntitiy> saveGoods(CreateAuctionRequestDTO dto, String username){

        ProductEntitiy productEntitiy;

        if(dto.isAuction()){
                AuctionRequestDTO auctionData=  new AuctionRequestDTO(dto.getTitle(),dto.getSellerUsername(),dto.getStartingPrice(),dto.getMinimumIncrement(),dto.getStartTime(),dto.getEndTime());
            AuctionResponseDTO response = auctionWebcliant.post()
                    .uri("/auctions")
                    .bodyValue(auctionData)
                    .retrieve()
                    .bodyToMono(AuctionResponseDTO.class)
                    .block();
             productEntitiy  =  productRepository.save(productDTOEntityl.DtoTOEntity(dto,username,response.getId().toString()));
        }else{
             productEntitiy  =  productRepository.save(productDTOEntityl.DtoTOEntity(dto,username,null));


        }





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


