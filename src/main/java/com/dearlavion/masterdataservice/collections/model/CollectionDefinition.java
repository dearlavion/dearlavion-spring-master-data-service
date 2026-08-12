package com.dearlavion.masterdataservice.collections.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The registry row for one reference-data collection — the single list clients read from
 * {@code GET /collections}, covering the 8 original types and admin-created ones alike.
 *
 * <p>The 8 originals are seeded here on startup (see CollectionBootstrap) rather than hardcoded as
 * a Java constant, so their labels are admin-editable and new collections sit alongside them as
 * peers. What still separates them is {@link #builtIn}: an original's values live in its own Mongo
 * collection behind its own controllers ({@code /destinations}), and the /travel survey and
 * kit-scoring engine reference its key by name — so it can be renamed but not deleted. A custom
 * collection's values all share {@code custom_collection_items}, partitioned by collectionKey.
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "collections")
public class CollectionDefinition {

    /** Stable identifier used in URLs and in store-engine's kit_settings, e.g. "destination" or "fabric". Immutable. */
    @Id
    private String key;

    /** Admin-editable display name, e.g. "Destinations". */
    private String label;

    /**
     * Where this collection's values are read from, relative to the service root:
     * {@code destinations} for a built-in, {@code collections/<key>/items} for a custom one. Admin
     * writes are at {@code /admin/<path>} in both cases, so this one field is all a client needs to
     * build every URL.
     */
    private String path;

    /** True for the 8 originals — renameable, never deletable. */
    private boolean builtIn;

    public CollectionDefinition(String key, String label, String path, boolean builtIn) {
        this.key = key;
        this.label = label;
        this.path = path;
        this.builtIn = builtIn;
    }

    /** Path convention for an admin-created collection, whose values have no dedicated endpoints. */
    public static String customPath(String key) {
        return "collections/" + key + "/items";
    }
}
