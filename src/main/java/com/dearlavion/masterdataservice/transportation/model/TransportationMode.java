package com.dearlavion.masterdataservice.transportation.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the transportation modes (e.g. Flight/Car/Train/Cruise) — powers the Travel Besty /travel survey and product tagging. */
@Document(collection = "transportation_modes")
public class TransportationMode extends ReferenceItem {
}
