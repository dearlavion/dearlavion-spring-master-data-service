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
        for (CollectionDefinition builtIn : CollectionRegistrySeed.BUILT_INS) {
            if (!repository.existsById(builtIn.getKey())) {
                repository.save(builtIn);
                inserted++;
            }
        }
        if (inserted > 0) {
            log.info("Registered {} built-in collection(s) in the collections registry.", inserted);
        }
    }
}
