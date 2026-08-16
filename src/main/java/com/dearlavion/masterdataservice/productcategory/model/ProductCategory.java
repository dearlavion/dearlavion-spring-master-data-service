package com.dearlavion.masterdataservice.productcategory.model;

import com.dearlavion.masterdataservice.common.ReferenceItem;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * What a product <em>is</em> (Clothing, Electronics, Travel Accessories…) — exactly one per
 * product, and the label the storefront shows and searches on.
 *
 * <p>Not to be confused with KitCategory, which is what a product is <em>packed in</em>
 * (Weather Kit, Toiletry Kit…) and is multi-valued. A rain jacket is one Clothing product that
 * belongs in the Weather Kit: the first drives the shop, the second drives the /travel survey.
 */
@Document(collection = "product_categories")
public class ProductCategory extends ReferenceItem {
}
