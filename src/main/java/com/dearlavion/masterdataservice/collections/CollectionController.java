package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import com.dearlavion.masterdataservice.collections.model.CustomCollectionItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Public reads, mirroring the built-in types' public GETs: the catalog of collections, and the
 * values inside a custom one. A built-in's values stay on its own endpoint ({@code /destinations}).
 */
@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionRegistryService registryService;
    private final CustomCollectionItemService itemService;

    @GetMapping
    public List<CollectionDefinition> list() {
        return registryService.listAll();
    }

    @GetMapping("/{key}/items")
    public List<CustomCollectionItem> listItems(@PathVariable String key) {
        return itemService.listAll(key);
    }
}
