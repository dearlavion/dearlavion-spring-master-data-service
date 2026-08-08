package com.dearlavion.masterdataservice.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Shared field shape for every reference-data type this service owns (destination, season,
 * party, transportation, activity, kitCategory, duration, gender). Each type is still its own
 * dedicated Mongo collection/Java class, not one generic axis-keyed collection — this base class
 * only exists to share fields and CRUD plumbing (see AbstractReferenceItemService).
 */
@Getter
@Setter
public abstract class ReferenceItem {

    @Id
    private String id;

    @Indexed(unique = true)
    private String value;

    private int order;

    private String emoji;

    private String subtext;
}
