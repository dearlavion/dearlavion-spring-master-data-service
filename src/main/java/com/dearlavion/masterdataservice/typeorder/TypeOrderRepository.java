package com.dearlavion.masterdataservice.typeorder;

import com.dearlavion.masterdataservice.typeorder.model.TypeOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeOrderRepository extends MongoRepository<TypeOrder, String> {
}
