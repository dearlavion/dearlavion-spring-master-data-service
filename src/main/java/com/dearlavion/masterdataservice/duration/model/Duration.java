package com.dearlavion.masterdataservice.duration.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Trip-length tier — fixed cardinality (day/short/medium/long): AdminDurationController rejects
 * add/delete, only value/order/subtext can be edited via the inherited update(). {@code code} is
 * the stable key store-engine's kit-sizing scoring uses; it's set only by the seed data, never
 * exposed via the API (CreateReferenceItemRequest/UpdateReferenceItemRequest have no code field).
 */
@Getter
@Setter
@Document(collection = "durations")
public class Duration extends ReferenceItem {
    private String code;
}
