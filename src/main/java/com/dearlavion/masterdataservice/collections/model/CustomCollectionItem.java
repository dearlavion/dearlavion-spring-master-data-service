package com.dearlavion.masterdataservice.collections.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * One value inside an admin-created collection. Deliberately does NOT extend {@link
 * com.dearlavion.masterdataservice.common.ReferenceItem}: that base class carries
 * {@code @Indexed(unique = true)} on {@code value}, which (with auto-index-creation enabled) would
 * make values unique across every custom collection at once — two collections could never both
 * contain "Small". Uniqueness here is per collection instead, via the compound index below.
 *
 * <p>Field names mirror ReferenceItem exactly so the JSON shape is identical to the 8 built-in
 * types and the frontend can treat every collection the same way.
 */
@Getter
@Setter
@Document(collection = "custom_collection_items")
@CompoundIndex(name = "collectionKey_value_unique", def = "{'collectionKey': 1, 'value': 1}", unique = true)
public class CustomCollectionItem {

    @Id
    private String id;

    /** Which registered collection this value belongs to — see CustomCollection.key. */
    private String collectionKey;

    private String value;

    private int order;

    private String emoji;

    private String subtext;
}
