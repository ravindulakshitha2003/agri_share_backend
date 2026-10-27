package com.example.auction2.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository
        extends JpaRepository<Auction, Long> {
    @Query("""
    SELECT a FROM Auction a
    WHERE a.status = :status
      AND a.endTime <= :now
    """)
    List<Auction> findExpiredAuctions(
            @Param("status") AuctionStatus status,
            @Param("now") Instant now
    );
    @Query(
            value = "SELECT * FROM auctions WHERE id = :id FOR UPDATE",
            nativeQuery = true
    )
    Optional<Auction> findByIdForUpdate(@Param("id") Long id);
}