package com.dearlavion.masterdataservice.health;

import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final MongoClient mongoClient;

    @GetMapping("/health")
    public Map<String, String> health() {
        try {
            mongoClient.listDatabaseNames().first();
            return Map.of("status", "UP");
        } catch (Exception e) {
            return Map.of("status", "DOWN");
        }
    }
}
