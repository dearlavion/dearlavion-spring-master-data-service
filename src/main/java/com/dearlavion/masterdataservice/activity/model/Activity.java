package com.dearlavion.masterdataservice.activity.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the trip activities (e.g. Hiking/Swimming) — powers the Travel Besty /travel survey and product tagging. */
@Document(collection = "activities")
public class Activity extends ReferenceItem {
}
