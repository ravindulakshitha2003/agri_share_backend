package com.example.suplier_service.controller;


import com.example.suplier_service.dto.CreateAuctionRequestDTO;
import com.example.suplier_service.dto.PurchesReqestDTO;
import com.example.suplier_service.dto.PurchesedRespondDTO;
import com.example.suplier_service.entitiy.ProductEntitiy;
import com.example.suplier_service.entitiy.PurchesEntitiy;
import com.example.suplier_service.service.PurchesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purches")
public class PurchesController {
    private  final PurchesService purchesService;

    public PurchesController(PurchesService purchesService) {
        this.purchesService = purchesService;
    }

    @PostMapping("/create")
    public ResponseEntity<PurchesedRespondDTO> create(@RequestBody PurchesReqestDTO purchesReqestDTO) {
            return  purchesService.save(purchesReqestDTO);
    }

}
