package com.dearlavion.masterdataservice.common;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReferenceItemRepository<T extends ReferenceItem> extends MongoRepository<T, String> {

    List<T> findAllByOrderByOrderAsc();

    Optional<T> findByValue(String value);
}
