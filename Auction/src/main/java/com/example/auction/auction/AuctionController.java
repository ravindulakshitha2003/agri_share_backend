package com.example.auction.auction;




import com.example.auction.dto.AuctionResponseDTO;
import com.example.auction.dto.CreateAuctionRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PSource;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public AuctionResponseDTO create(

            @Valid @RequestBody CreateAuctionRequestDTO request) {
        System.out.println("recived for aucuion");
        return auctionService.create(request);
    }

    @GetMapping
    public List<AuctionResponseDTO> getAll() {
        return auctionService.getAll();
    }

    @GetMapping("/{id}")
    public AuctionResponseDTO getById(@PathVariable Long id) {
        return auctionService.getById(id);
    }

    @PostMapping("/{id}/close")
    public AuctionResponseDTO close(
            @PathVariable Long id,
            @RequestParam String sellerUsername) {
        return auctionService.close(id, sellerUsername);
    }

    @GetMapping("/test")
    public void   test(){
        auctionService.testAuctionLock();
    }
}
