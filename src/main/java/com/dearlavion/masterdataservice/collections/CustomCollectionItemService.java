package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import com.dearlavion.masterdataservice.collections.model.CustomCollectionItem;
import com.dearlavion.masterdataservice.common.exception.ConflictException;
import com.dearlavion.masterdataservice.common.exception.NotFoundException;
import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.common.request.UpdateReferenceItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CRUD for the values inside an admin-created collection — the generic counterpart to
 * AbstractReferenceItemService, which serves one dedicated Mongo collection per built-in type.
 * Reuses the same request records, so a custom collection's values accept exactly the same fields
 * as a built-in's.
 */
@Service
@RequiredArgsConstructor
public class CustomCollectionItemService {

    private final CustomCollectionItemRepository repository;
    private final CollectionRegistryService registry;

    public List<CustomCollectionItem> listAll(String collectionKey) {
        requireCustomCollection(collectionKey);
        return repository.findByCollectionKeyOrderByOrderAsc(collectionKey);
    }

    public CustomCollectionItem create(String collectionKey, CreateReferenceItemRequest input) {
        requireCustomCollection(collectionKey);
        CustomCollectionItem item = new CustomCollectionItem();
        item.setCollectionKey(collectionKey);
        item.setValue(input.value());
        item.setOrder(input.order() != null ? input.order() : 0);
        item.setEmoji(input.emoji());
        item.setSubtext(input.subtext());
        return save(item, input.value());
    }

    public CustomCollectionItem update(String collectionKey, String id, UpdateReferenceItemRequest patch) {
        requireCustomCollection(collectionKey);
        CustomCollectionItem item = repository.findByIdAndCollectionKey(id, collectionKey)
                .orElseThrow(() -> new NotFoundException("Value not found"));
        if (patch.value() != null) item.setValue(patch.value());
        if (patch.order() != null) item.setOrder(patch.order());
        if (patch.emoji() != null) item.setEmoji(patch.emoji());
        if (patch.subtext() != null) item.setSubtext(patch.subtext());
        return save(item, patch.value());
    }

    public void delete(String collectionKey, String id) {
        requireCustomCollection(collectionKey);
        CustomCollectionItem item = repository.findByIdAndCollectionKey(id, collectionKey)
                .orElseThrow(() -> new NotFoundException("Value not found"));
        repository.delete(item);
    }

    private CustomCollectionItem save(CustomCollectionItem item, String attemptedValue) {
        try {
            return repository.save(item);
        } catch (DuplicateKeyException e) {
            // Unique on (collectionKey, value) — two collections may both hold "Small", one may not.
            throw new ConflictException("\"" + attemptedValue + "\" already exists in this collection");
        }
    }

    /**
     * A built-in's values are served by its own controllers ({@code /destinations}) and stored in
     * its own Mongo collection, so routing them through here would silently read and write an empty
     * parallel list.
     */
    private void requireCustomCollection(String collectionKey) {
        CollectionDefinition definition = registry.findByKey(collectionKey);
        if (definition.isBuiltIn()) {
            throw new ConflictException(
                    "\"" + definition.getLabel() + "\" is a built-in collection — use /" + definition.getPath() + " instead");
        }
    }
}
