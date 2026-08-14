package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Inserts any missing built-in registry row on startup, so {@code GET /collections} always lists
 * all 8 originals alongside admin-created ones. Unlike SeedRunner this is not behind the "seed"
 * profile — the registry is structural, needed on every boot, not sample data.
 *
 * <p>Insert-only on purpose: an existing row is left exactly as-is, so an admin's renamed label
 * isn't reverted on the next restart.
 *
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class CollectionBootstrap implements ApplicationRunner {

    private final CollectionDefinitionRepository repository;

    @Override
    public void run(ApplicationArguments args) {
        int inserted = 0;
        int backfilled = 0;
        for (CollectionDefinition builtIn : CollectionRegistrySeed.BUILT_INS) {
            CollectionDefinition existing = repository.findById(builtIn.getKey()).orElse(null);
            if (existing == null) {
                repository.save(builtIn);
                inserted++;
            } else if (existing.getStorage() == null || existing.getStorage().isBlank()) {
                // Row predates the per-collection storage field — fill in just that, leaving an
                // admin-renamed label alone.
                existing.setStorage(builtIn.getStorage());
                repository.save(existing);
                backfilled++;
            }
        }
        // Custom rows created before the storage field existed keep pointing at nothing, which
        // would fail every read; derive theirs from the key, same rule create() now uses.
        for (CollectionDefinition custom : repository.findAll()) {
            if (!custom.isBuiltIn() && (custom.getStorage() == null || custom.getStorage().isBlank())) {
                custom.setStorage(CollectionDefinition.storageFor(custom.getKey()));
                repository.save(custom);
                backfilled++;
            }
        }
        if (inserted > 0) {
            log.info("Registered {} built-in collection(s) in the collections registry.", inserted);
        }
        if (backfilled > 0) {
            log.info("Backfilled the storage collection name on {} registry row(s).", backfilled);
        }
    }
}
