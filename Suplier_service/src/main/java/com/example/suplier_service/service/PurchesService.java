package com.example.suplier_service.service;

import com.example.suplier_service.dto.PurchesReqestDTO;
import com.example.suplier_service.dto.PurchesedRespondDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.entitiy.PurchesEntitiy;
import com.example.suplier_service.repository.ProductRepository;
import com.example.suplier_service.repository.PruchesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PurchesService {
    private  final PruchesRepository pruchesRepository;
    private  final ProductRepository productRepository;

    public PurchesService(PruchesRepository pruchesRepository, ProductRepository productRepository) {
        this.pruchesRepository = pruchesRepository;

        this.productRepository = productRepository;
    }
    @Transactional
    public ResponseEntity<PurchesedRespondDTO> save(PurchesReqestDTO dto) {

        if (dto == null) {
            return  new ResponseEntity<>(new PurchesedRespondDTO(true, "no data recived"),HttpStatus.BAD_REQUEST);
        }
        ProductEntitiy product = productRepository.findById(dto.getProductid()).orElse(null);
        if (product == null) {
            return  new ResponseEntity<>(new PurchesedRespondDTO(true, "no data recived"),HttpStatus.BAD_REQUEST);
        }

        if (product.getQuantity().longValue() < dto.getQuntitiy()) {
            return  new ResponseEntity<>(new PurchesedRespondDTO(true, "no data recived"),HttpStatus.BAD_REQUEST);

        }
        PurchesEntitiy respond = new PurchesEntitiy(dto.getProductid(), dto.getUsername(), dto.getQuntitiy(), dto.getUnit());
        PurchesEntitiy data = pruchesRepository.save(respond);

        if(data.getId()== null){
            return  new ResponseEntity<>(new PurchesedRespondDTO(true, "no data recived"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( new  PurchesedRespondDTO(false, "successfull"),HttpStatus.OK);

    }


}
