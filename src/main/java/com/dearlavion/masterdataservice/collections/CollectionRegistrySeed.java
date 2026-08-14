package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;

import java.util.List;

/**
 * Registry rows for the 8 original types, inserted on first startup by {@link CollectionBootstrap}.
 * Seed data, not a runtime constant: once a row exists the database is the source of truth, so an
 * admin renaming "Parties" to "Who's Going" survives every restart. Keys must match the
 * {@code @RequestMapping} paths on each type's controllers, and the keys store-engine-v2's
 * kit_settings document orders the survey by.
 */
public final class CollectionRegistrySeed {

    public static final List<CollectionDefinition> BUILT_INS = List.of(
            new CollectionDefinition("destination", "Destinations", "destinations", true, "destinations"),
            new CollectionDefinition("season", "Seasons", "seasons", true, "seasons"),
            new CollectionDefinition("party", "Parties", "parties", true, "parties"),
            new CollectionDefinition("transportation", "Transportation", "transportation-modes", true, "transportation_modes"),
            new CollectionDefinition("activity", "Activities", "activities", true, "activities"),
            new CollectionDefinition("kitCategory", "Kit Categories", "kit-categories", true, "kit_categories"),
            new CollectionDefinition("duration", "Durations", "durations", true, "durations"),
            new CollectionDefinition("gender", "Genders", "genders", true, "genders")
    );

    private CollectionRegistrySeed() {
    }
}
