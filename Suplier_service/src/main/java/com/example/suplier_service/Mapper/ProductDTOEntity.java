package com.example.suplier_service.Mapper;

import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class ProductDTOEntity {


    public ProductEntitiy DtoTOEntity(ProductRequestDTO productRequestDTO,String username,String autionid) {
        return new ProductEntitiy(
                productRequestDTO.getCategory(),
                username,
                productRequestDTO.getProductName(),
                productRequestDTO.getQuantity(),
                productRequestDTO.getUnit(),
                productRequestDTO.getPrice(),
                productRequestDTO.getCondition(),
                productRequestDTO.getPackaging(),
                productRequestDTO.getMinimumQuantity(),
                productRequestDTO.getLocation(),
                productRequestDTO.getAvailableDate(),
                productRequestDTO.getAvailableTime(),
                productRequestDTO.getDeadlineDate(),
                productRequestDTO.getDeadlineTime(),
                productRequestDTO.getFreshness(),
                productRequestDTO.getStorage(),
                productRequestDTO.getUrgency(),
                productRequestDTO.getCanDeliver(),
                productRequestDTO.getPriceNegotiable(),
                productRequestDTO.getDescription(),
                productRequestDTO.isAuction()

        );
    }



}
