package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import com.dearlavion.masterdataservice.collections.model.CustomCollectionItem;
import com.dearlavion.masterdataservice.collections.request.CreateCollectionRequest;
import com.dearlavion.masterdataservice.collections.request.UpdateCollectionRequest;
import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.common.request.UpdateReferenceItemRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ROLE_ADMIN writes (gated by SecurityConfig's {@code /admin/**} rule) for collections and, for
 * custom ones, their values. The item paths deliberately mirror the built-ins' shape —
 * {@code /admin/<path>} and {@code /admin/<path>/{id}} — so a client builds every URL from the
 * {@code path} field alone, whichever kind of collection it is.
 */
@RestController
@RequestMapping("/admin/collections")
@RequiredArgsConstructor
public class AdminCollectionController {

    private final CollectionRegistryService registryService;
    private final CustomCollectionItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionDefinition create(@Valid @RequestBody CreateCollectionRequest body) {
        return registryService.create(body);
    }

    /** Renames any collection, built-in included — only the key is immutable. */
    @PutMapping("/{key}")
    public CollectionDefinition rename(@PathVariable String key, @Valid @RequestBody UpdateCollectionRequest body) {
        return registryService.rename(key, body.label());
    }

    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String key) {
        registryService.delete(key);
    }

    @PostMapping("/{key}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomCollectionItem createItem(@PathVariable String key, @Valid @RequestBody CreateReferenceItemRequest body) {
        return itemService.create(key, body);
    }

    @PutMapping("/{key}/items/{id}")
    public CustomCollectionItem updateItem(
            @PathVariable String key, @PathVariable String id, @RequestBody UpdateReferenceItemRequest body) {
        return itemService.update(key, id, body);
    }

    @DeleteMapping("/{key}/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable String key, @PathVariable String id) {
        itemService.delete(key, id);
    }
}
