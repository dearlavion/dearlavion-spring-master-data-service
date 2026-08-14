package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import com.dearlavion.masterdataservice.collections.model.CustomCollectionItem;
import com.dearlavion.masterdataservice.common.exception.ConflictException;
import com.dearlavion.masterdataservice.common.exception.NotFoundException;
import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.common.request.UpdateReferenceItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CRUD for the values inside an admin-created collection — the generic counterpart to
 * AbstractReferenceItemService, which serves one dedicated Mongo collection per built-in type.
 *
 * <p>Each custom collection gets its own Mongo collection too (named by
 * {@link CollectionDefinition#storageFor}), so the data is browsable exactly like {@code
 * destinations} or {@code seasons} rather than hiding inside one shared, key-partitioned lump. What
 * a built-in gets from a Java class and a repository, these get from MongoTemplate plus the
 * collection name pinned on the registry row.
 *
 * <p>Reuses the same request records as the built-ins, so a custom collection's values accept
 * exactly the same fields.
 */
@Service
@RequiredArgsConstructor
public class CustomCollectionItemService {

    private final MongoTemplate mongoTemplate;
    private final CollectionRegistryService registry;

    public List<CustomCollectionItem> listAll(String collectionKey) {
        String storage = requireCustomCollection(collectionKey).getStorage();
        return mongoTemplate.find(new Query().with(Sort.by(Sort.Direction.ASC, "order")), CustomCollectionItem.class, storage);
    }

    public CustomCollectionItem create(String collectionKey, CreateReferenceItemRequest input) {
        String storage = requireCustomCollection(collectionKey).getStorage();
        CustomCollectionItem item = new CustomCollectionItem();
        item.setValue(input.value());
        item.setOrder(input.order() != null ? input.order() : 0);
        item.setEmoji(input.emoji());
        item.setSubtext(input.subtext());
        return save(item, storage, input.value());
    }

    public CustomCollectionItem update(String collectionKey, String id, UpdateReferenceItemRequest patch) {
        String storage = requireCustomCollection(collectionKey).getStorage();
        CustomCollectionItem item = findOne(storage, id);
        if (patch.value() != null) item.setValue(patch.value());
        if (patch.order() != null) item.setOrder(patch.order());
        if (patch.emoji() != null) item.setEmoji(patch.emoji());
        if (patch.subtext() != null) item.setSubtext(patch.subtext());
        return save(item, storage, patch.value());
    }

    public void delete(String collectionKey, String id) {
        String storage = requireCustomCollection(collectionKey).getStorage();
        mongoTemplate.remove(findOne(storage, id), storage);
    }

    /**
     * Creates the Mongo collection with the same unique index on {@code value} the built-ins carry,
     * so a custom collection behaves identically from the first write. Called when a collection is
     * registered rather than lazily, so an empty collection is still visible in the database.
     */
    public void initStorage(String storage) {
        if (!mongoTemplate.collectionExists(storage)) {
            mongoTemplate.createCollection(storage);
        }
        mongoTemplate.indexOps(storage).ensureIndex(new Index().on("value", Sort.Direction.ASC).unique());
    }

    /** Drops the whole Mongo collection — used when its registry row is deleted. */
    public void dropStorage(String storage) {
        if (storage != null && mongoTemplate.collectionExists(storage)) {
            mongoTemplate.dropCollection(storage);
        }
    }

    private CustomCollectionItem findOne(String storage, String id) {
        CustomCollectionItem item = mongoTemplate.findById(id, CustomCollectionItem.class, storage);
        if (item == null) throw new NotFoundException("Value not found");
        return item;
    }

    private CustomCollectionItem save(CustomCollectionItem item, String storage, String attemptedValue) {
        try {
            return mongoTemplate.save(item, storage);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("\"" + attemptedValue + "\" already exists in this collection");
        }
    }

    /**
     * A built-in's values are served by its own controllers ({@code /destinations}) and mapped to a
     * dedicated Java class, so routing them through here would bypass that type entirely.
     */
    private CollectionDefinition requireCustomCollection(String collectionKey) {
        CollectionDefinition definition = registry.findByKey(collectionKey);
        if (definition.isBuiltIn()) {
            throw new ConflictException(
                    "\"" + definition.getLabel() + "\" is a built-in collection — use /" + definition.getPath() + " instead");
        }
        if (definition.getStorage() == null || definition.getStorage().isBlank()) {
            throw new IllegalStateException("Collection \"" + collectionKey + "\" has no storage collection recorded");
        }
        return definition;
    }
}
