package com.example.auction.auction;

import com.example.auction.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository
        extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent>
    findTop100ByPublishedFalseOrderByIdAsc();
}