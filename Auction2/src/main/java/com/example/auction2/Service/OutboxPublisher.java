
        package com.example.auction2.Service;

import com.example.auction2.auction.OutboxEvent;
import com.example.auction2.auction.OutboxEventRepository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
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

        List<OutboxEvent> events =
                outboxRepository
                        .findTop100ByPublishedFalseOrderByIdAsc();

        for (OutboxEvent event : events) {
            try {
                // Send the event to Kafka and wait for confirmation.
                kafkaTemplate.send(
                        "notification-events",
                        event.getEventId(),
                        event.getPayload()
                ).get(10, TimeUnit.SECONDS);

                // Mark as published only after Kafka confirms.
                markPublished(event);

            } catch (Exception ex) {
                System.err.println(
                        "Could not publish event "
                                + event.getEventId()
                                + ": "
                                + ex.getMessage()
                );
            }
        }
    }

    private void markPublished(OutboxEvent event) {

        OutboxEvent current = outboxRepository
                .findById(event.getId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Outbox event not found: "
                                        + event.getId()
                        )
                );

        current.markPublished();

        outboxRepository.save(current);
    }
}
