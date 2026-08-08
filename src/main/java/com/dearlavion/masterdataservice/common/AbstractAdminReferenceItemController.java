package com.dearlavion.masterdataservice.common;

import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.common.request.UpdateReferenceItemRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base for the admin write controller (ROLE_ADMIN-gated by SecurityConfig) of a single
 * reference-data type. Duration is the one type that overrides create()/remove() to reject them
 * (fixed cardinality) — see AdminDurationController.
 */
public abstract class AbstractAdminReferenceItemController<T extends ReferenceItem> {

    protected final AbstractReferenceItemService<T> service;

    protected AbstractAdminReferenceItemController(AbstractReferenceItemService<T> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@Valid @RequestBody CreateReferenceItemRequest body) {
        return service.create(body);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable String id, @RequestBody UpdateReferenceItemRequest body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String id) {
        service.delete(id);
    }
}
