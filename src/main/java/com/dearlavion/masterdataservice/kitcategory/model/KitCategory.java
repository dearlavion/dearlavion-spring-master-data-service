package com.dearlavion.masterdataservice.kitcategory.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/** One of the kit product categories (e.g. Essentials/Clothing) — used to group products in a kit. */
@Document(collection = "kit_categories")
public class KitCategory extends ReferenceItem {
}
