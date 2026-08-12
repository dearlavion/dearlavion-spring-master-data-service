package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectionDefinitionRepository extends MongoRepository<CollectionDefinition, String> {

    List<CollectionDefinition> findAllByOrderByLabelAsc();
}
