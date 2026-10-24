package com.example.auction.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuctionRepository
        extends JpaRepository<Auction, Long> {

    @Query(
            value = "SELECT * FROM auctions WHERE id = :id FOR UPDATE",
            nativeQuery = true
    )
    Optional<Auction> findByIdForUpdate(@Param("id") Long id);
}