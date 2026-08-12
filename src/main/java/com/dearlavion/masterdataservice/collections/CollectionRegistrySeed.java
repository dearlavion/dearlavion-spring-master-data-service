package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;

import java.util.List;

/**
 * Registry rows for the 8 original types, inserted on first startup by {@link CollectionBootstrap}.
 * Seed data, not a runtime constant: once a row exists the database is the source of truth, so an
 * admin renaming "Parties" to "Who's Going" survives every restart. Keys must match
 * {@code KitSettings.DEFAULT_ORDER} and the {@code @RequestMapping} paths on each type's controllers.
 */
public final class CollectionRegistrySeed {

    public static final List<CollectionDefinition> BUILT_INS = List.of(
            new CollectionDefinition("destination", "Destinations", "destinations", true),
            new CollectionDefinition("season", "Seasons", "seasons", true),
            new CollectionDefinition("party", "Parties", "parties", true),
            new CollectionDefinition("transportation", "Transportation", "transportation-modes", true),
            new CollectionDefinition("activity", "Activities", "activities", true),
            new CollectionDefinition("kitCategory", "Kit Categories", "kit-categories", true),
            new CollectionDefinition("duration", "Durations", "durations", true),
            new CollectionDefinition("gender", "Genders", "genders", true)
    );

    private CollectionRegistrySeed() {
    }
}
