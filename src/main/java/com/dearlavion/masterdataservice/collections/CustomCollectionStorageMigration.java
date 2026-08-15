package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * One-time move of custom collection values out of the shared, key-partitioned
 * {@code custom_collection_items} collection and into one Mongo collection per registered
 * collection — so an admin-created collection is browsable in the database exactly like a built-in
 * (its values used to be invisible unless you knew to filter by collectionKey).
 *
 * <p>Runs after CollectionBootstrap has backfilled each registry row's {@code storage} name. The
 * legacy collection is dropped once copied, so this never runs twice.
 */
@Slf4j
@Component
// One-time move, already applied everywhere it was needed, and it drops the legacy
// collection when it runs — off unless explicitly asked for. Kept rather than deleted so a
// database still on the old shared-collection layout can be migrated with
// APP_CUSTOM_COLLECTION_MIGRATION_ENABLED=true.
@ConditionalOnProperty(name = "app.custom-collection-migration-enabled", havingValue = "true")
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
@RequiredArgsConstructor
public class CustomCollectionStorageMigration implements ApplicationRunner {

    private static final String LEGACY_COLLECTION = "custom_collection_items";

    private final MongoTemplate mongoTemplate;
    private final CollectionDefinitionRepository repository;
    private final CustomCollectionItemService itemService;

    @Override
    public void run(ApplicationArguments args) {
        if (!mongoTemplate.collectionExists(LEGACY_COLLECTION)) return;

        int moved = 0;
        for (CollectionDefinition definition : repository.findAll()) {
            if (definition.isBuiltIn() || definition.getStorage() == null) continue;

            List<Document> legacyItems = mongoTemplate.getCollection(LEGACY_COLLECTION)
                    .find(new Document("collectionKey", definition.getKey()))
                    .into(new java.util.ArrayList<>());
            if (legacyItems.isEmpty()) continue;

            itemService.initStorage(definition.getStorage());
            for (Document item : legacyItems) {
                // collectionKey is what the shared collection was partitioned by; a dedicated
                // collection makes it redundant, so it doesn't come along.
                item.remove("collectionKey");
                item.put("_class", "com.dearlavion.masterdataservice.collections.model.CustomCollectionItem");
                mongoTemplate.getCollection(definition.getStorage()).insertOne(item);
                moved++;
            }
            log.info("Moved {} value(s) into the {} collection.", legacyItems.size(), definition.getStorage());
        }

        mongoTemplate.getCollection(LEGACY_COLLECTION).drop();
        log.info("Migrated {} custom value(s) to per-collection storage; dropped {}.", moved, LEGACY_COLLECTION);
    }
}
