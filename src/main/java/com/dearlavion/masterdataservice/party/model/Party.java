package com.dearlavion.masterdataservice.party.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the travel party sizes (e.g. Solo/Group) — powers the Travel Besty /travel survey and product tagging. */
@Document(collection = "parties")
public class Party extends ReferenceItem {
}
