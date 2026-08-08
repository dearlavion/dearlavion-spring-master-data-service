package com.dearlavion.masterdataservice.common;

import com.dearlavion.masterdataservice.common.exception.ConflictException;
import com.dearlavion.masterdataservice.common.exception.NotFoundException;
import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.common.request.UpdateReferenceItemRequest;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.function.Supplier;

/**
 * Generic CRUD for a single reference-data type's collection. Subclasses (one per type) just
 * supply the repository, a factory for a new empty entity, and the type's display name for error
 * messages — everything else is identical across all 8 types. Duration overrides only its admin
 * controller (not this service) to enforce fixed cardinality on create/delete; update() here is
 * reused unchanged.
 */
public abstract class AbstractReferenceItemService<T extends ReferenceItem> {

    protected final ReferenceItemRepository<T> repository;
    private final Supplier<T> factory;
    private final String typeName;

    protected AbstractReferenceItemService(ReferenceItemRepository<T> repository, Supplier<T> factory, String typeName) {
        this.repository = repository;
        this.factory = factory;
        this.typeName = typeName;
    }

    public List<T> listAll() {
        return repository.findAllByOrderByOrderAsc();
    }

    public T findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(typeName + " not found"));
    }

    public T create(CreateReferenceItemRequest input) {
        T item = factory.get();
        item.setValue(input.value());
        item.setOrder(input.order() != null ? input.order() : 0);
        item.setEmoji(input.emoji());
        item.setSubtext(input.subtext());
        try {
            return repository.save(item);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("\"" + input.value() + "\" already exists");
        }
    }

    public T update(String id, UpdateReferenceItemRequest patch) {
        T item = repository.findById(id).orElseThrow(() -> new NotFoundException(typeName + " not found"));
        if (patch.value() != null) item.setValue(patch.value());
        if (patch.order() != null) item.setOrder(patch.order());
        if (patch.emoji() != null) item.setEmoji(patch.emoji());
        if (patch.subtext() != null) item.setSubtext(patch.subtext());
        try {
            return repository.save(item);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("\"" + patch.value() + "\" already exists");
        }
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(typeName + " not found");
        }
        repository.deleteById(id);
    }

    /** Idempotent upsert by value — used only by SeedRunner. */
    public void upsert(String value, Integer order, String emoji, String subtext) {
        T item = repository.findByValue(value).orElseGet(factory);
        item.setValue(value);
        item.setOrder(order != null ? order : 0);
        item.setEmoji(emoji);
        item.setSubtext(subtext);
        repository.save(item);
    }
}
