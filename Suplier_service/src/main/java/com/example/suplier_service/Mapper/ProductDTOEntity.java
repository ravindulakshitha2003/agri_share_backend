package com.example.suplier_service.Mapper;

import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;

public class ProductDTOEntity {
    private ProductEntitiy productEntitiy;
    private ProductRequestDTO productRequestDTO;


    public ProductDTOEntity(ProductEntitiy productEntitiy) {
        this.productEntitiy = productEntitiy;

    }
    public ProductDTOEntity(ProductRequestDTO productRequestDTO) {

        this.productRequestDTO = productRequestDTO;
    }


    public ProductEntitiy DtoTOEntity(ProductRequestDTO productRequestDTO) {
        return new ProductEntitiy(
                productRequestDTO.getCategory(),
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
                productRequestDTO.getDescription()
        );
    }



}
