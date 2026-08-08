package com.dearlavion.masterdataservice.destination.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the trip destinations (e.g. Beach/Mountain/City) — powers the Travel Besty /travel survey and product tagging. */
@Document(collection = "destinations")
public class Destination extends ReferenceItem {
}
