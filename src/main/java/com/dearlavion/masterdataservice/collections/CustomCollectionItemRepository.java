package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CustomCollectionItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomCollectionItemRepository extends MongoRepository<CustomCollectionItem, String> {

    List<CustomCollectionItem> findByCollectionKeyOrderByOrderAsc(String collectionKey);

    Optional<CustomCollectionItem> findByIdAndCollectionKey(String id, String collectionKey);

    void deleteByCollectionKey(String collectionKey);
}
