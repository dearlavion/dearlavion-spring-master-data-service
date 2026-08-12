package com.dearlavion.masterdataservice.kitsettings;

import com.dearlavion.masterdataservice.kitsettings.model.KitSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * One-time carry-over from the old {@code type_order} collection, which held just the survey's
 * question order, into {@code kit_settings}, which holds that plus each question's
 * optional/required and single/multiple behaviour. Without this an admin's saved order would
 * silently revert to the default on upgrade.
 *
 * <p>Runs before {@link com.dearlavion.masterdataservice.collections.CollectionBootstrap}'s peers
 * but after nothing in particular — it only touches its own two collections. The old collection is
 * dropped once copied, so this is a true rename and the migration never runs twice.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class KitSettingsMigration implements ApplicationRunner {

    private static final String LEGACY_COLLECTION = "type_order";
    private static final String LEGACY_ID = "type_order";

    private final MongoTemplate mongoTemplate;
    private final KitSettingsRepository repository;

    @Override
    @SuppressWarnings("unchecked")
    public void run(ApplicationArguments args) {
        if (!mongoTemplate.collectionExists(LEGACY_COLLECTION)) return;

        Document legacy = mongoTemplate.getCollection(LEGACY_COLLECTION)
                .find(new Document("_id", LEGACY_ID))
                .first();

        // Only carry the order over if kit_settings hasn't been written yet — a newer saved value
        // must never be clobbered by the legacy one.
        if (legacy != null && repository.findById(KitSettings.SINGLETON_ID).isEmpty()) {
            List<String> order = (List<String>) legacy.get("order");
            if (order != null && !order.isEmpty()) {
                KitSettings settings = new KitSettings();
                settings.setOrder(order);
                repository.save(settings);
                log.info("Migrated survey question order from type_order into kit_settings.");
            }
        }

        mongoTemplate.getCollection(LEGACY_COLLECTION).drop();
        log.info("Dropped the legacy type_order collection.");
    }
}
