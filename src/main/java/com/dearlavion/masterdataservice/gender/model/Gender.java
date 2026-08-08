package com.dearlavion.masterdataservice.gender.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the traveler gender options — used to tailor kit recommendations. */
@Document(collection = "genders")
public class Gender extends ReferenceItem {
}
