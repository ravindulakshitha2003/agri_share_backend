package com.example.suplier_service.controller;


import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntitiy> create(ProductRequestDTO productRequestDTO){
        return productService.saveGoods(productRequestDTO);
    }
    @GetMapping("/suppliers")
    public String getSuppliers() {
        return "Protected supplier data";
    }




}
