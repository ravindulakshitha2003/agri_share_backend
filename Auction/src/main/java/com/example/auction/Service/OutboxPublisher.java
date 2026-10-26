package com.example.auction.Service;




import com.example.auction.auction.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class OutboxPublisher {

    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxPublisher(
            OutboxEventRepository outboxRepository,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedDelay = 1000)
    public void publishPendingEvents() {

        var events =
                outboxRepository
                        .findTop100ByPublishedFalseOrderByIdAsc();

        for (com.example.auction.entity.OutboxEvent event : events) {
            try {
                // Wait for Kafka to confirm the send.
                kafkaTemplate.send(
                        "notification-events",
                        event.getEventId(),
                        event.getPayload()
                ).get(10, TimeUnit.SECONDS);

                markPublished(event);

            } catch (Exception ex) {
                System.err.println(
                        "Could not publish event "
                                + event.getEventId()
                                + ": " + ex.getMessage()
                );
            }
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public void markPublished(com.example.auction.entity.OutboxEvent event) {
        com.example.auction.entity.OutboxEvent current = outboxRepository
                .findById(event.getId())
                .orElseThrow();

        current.markPublished();
        outboxRepository.save(current);
    }
}