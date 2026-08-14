package com.dearlavion.masterdataservice.collections.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * One value inside an admin-created collection. Carries no {@code @Document}: each custom
 * collection has its own Mongo collection, named on its registry row, and
 * CustomCollectionItemService passes that name to MongoTemplate on every call — so one class serves
 * them all without binding to a single collection.
 *
 * <p>Deliberately does NOT extend {@link com.dearlavion.masterdataservice.common.ReferenceItem}:
 * that base class declares its unique index via annotation, which only works for a type bound to
 * one fixed collection. CustomCollectionItemService.initStorage() creates the equivalent unique
 * index on {@code value} per collection instead.
 *
 * <p>Field names mirror ReferenceItem exactly so the JSON shape is identical to the 8 built-in
 * types and the frontend can treat every collection the same way.
 */
@Getter
@Setter
public class CustomCollectionItem {

    @Id
    private String id;

    private String value;

    private int order;

    private String emoji;

    private String subtext;
}
