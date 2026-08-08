package com.dearlavion.masterdataservice.typeorder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Single-document singleton ({@code _id: "type_order"}) holding the admin-configurable order of
 * the 8 reference-data types — mirrors store-engine-v2's AxisOrder, for a future Kit
 * Settings/Travel-quiz cutover to this service.
 */
@Getter
@Setter
@Document(collection = "type_order")
public class TypeOrder {

    public static final String SINGLETON_ID = "type_order";

    public static final List<String> DEFAULT_ORDER = List.of(
            "destination", "season", "duration", "party", "transportation", "activity", "kitCategory", "gender"
    );

    @Id
    private String id = SINGLETON_ID;

    private List<String> order = DEFAULT_ORDER;
}
