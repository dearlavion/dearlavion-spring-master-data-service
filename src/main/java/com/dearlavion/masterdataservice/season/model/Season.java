package com.dearlavion.masterdataservice.season.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the trip seasons (e.g. Summer/Winter/Rainy) — powers the Travel Besty /travel survey and product tagging. */
@Document(collection = "seasons")
public class Season extends ReferenceItem {
}
