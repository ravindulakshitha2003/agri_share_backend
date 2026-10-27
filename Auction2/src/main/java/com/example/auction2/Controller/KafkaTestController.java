package com.example.auction2.Controller;




import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka-test")
public class KafkaTestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaTestController(
            KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendTestMessage(
            @RequestBody String message) {

        kafkaTemplate.send(
                "auction-ended-events",
                message
        );

        return ResponseEntity.ok(
                "Test message sent to Kafka"
        );
    }
}

