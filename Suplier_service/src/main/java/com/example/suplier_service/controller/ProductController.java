package com.example.suplier_service.controller;
import com.example.suplier_service.dto.CreateAuctionRequestDTO;
import com.example.suplier_service.dto.ProductRequestDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntitiy> create(@RequestBody CreateAuctionRequestDTO productRequestDTO, @AuthenticationPrincipal String username){
        System.out.println(username);
        return productService.saveGoods(productRequestDTO,username);
    }
    @GetMapping("/health")
    public String getSuppliers() {
        return "Protected supplier data";
    }
    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductEntitiy>> getall() {
        return productService.getall();
    }


    @GetMapping("/allproductUser")
    public ResponseEntity<List<ProductEntitiy>> getbyusrname(@RequestParam String username) {

        return productService.getallbyusername(username);
    }







}
