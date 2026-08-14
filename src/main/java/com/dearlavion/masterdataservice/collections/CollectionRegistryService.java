package com.dearlavion.masterdataservice.collections;

import com.dearlavion.masterdataservice.collections.model.CollectionDefinition;
import com.dearlavion.masterdataservice.collections.request.CreateCollectionRequest;
import com.dearlavion.masterdataservice.common.exception.ConflictException;
import com.dearlavion.masterdataservice.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/** Create/rename/delete the collections themselves. Their values are handled by {@link CustomCollectionItemService}. */
@Service
@RequiredArgsConstructor
public class CollectionRegistryService {

    private final CollectionDefinitionRepository repository;
    // Lazily resolved: CustomCollectionItemService depends on this service, so constructor
    // injection either way round would be a cycle.
    private final org.springframework.beans.factory.ObjectProvider<CustomCollectionItemService> itemService;

    public List<CollectionDefinition> listAll() {
        return repository.findAllByOrderByLabelAsc();
    }

    public Set<String> allKeys() {
        return repository.findAll().stream().map(CollectionDefinition::getKey).collect(Collectors.toSet());
    }

    public CollectionDefinition findByKey(String key) {
        return repository.findById(key).orElseThrow(() -> new NotFoundException("Collection not found: " + key));
    }

    public CollectionDefinition create(CreateCollectionRequest input) {
        String label = input.label().trim();
        String key = input.key() != null && !input.key().isBlank() ? input.key() : deriveKey(label);
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Could not derive a key from \"" + label + "\" — supply one explicitly");
        }
        if (repository.existsById(key)) {
            throw new ConflictException("A collection with key \"" + key + "\" already exists");
        }

        // Each collection gets its own Mongo collection, so the derived name must not land on one
        // already in use — e.g. a new collection keyed "destinations" would otherwise resolve onto
        // the built-in Destination type's own collection.
        String storage = CollectionDefinition.storageFor(key);
        boolean storageTaken = repository.findAll().stream()
                .anyMatch(existing -> storage.equals(existing.getStorage()));
        if (storageTaken) {
            throw new ConflictException("\"" + label + "\" would collide with an existing collection's storage (" + storage + ")");
        }

        CollectionDefinition saved =
                repository.save(new CollectionDefinition(key, label, CollectionDefinition.customPath(key), false, storage));
        // Created up front (with the same unique index the built-ins carry) so a brand-new
        // collection is visible in the database before its first value is added.
        itemService.getObject().initStorage(storage);
        return saved;
    }

    public CollectionDefinition rename(String key, String label) {
        CollectionDefinition definition = findByKey(key);
        definition.setLabel(label.trim());
        return repository.save(definition);
    }

    /**
     * Deletes a custom collection and every value in it. Built-ins are refused: their values live in
     * their own Mongo collections behind dedicated controllers, and the /travel survey and
     * kit-scoring engine reference those keys by name, so dropping one would break survey scoring
     * rather than just hiding a list.
     */
    public void delete(String key) {
        CollectionDefinition definition = findByKey(key);
        if (definition.isBuiltIn()) {
            throw new ConflictException("\"" + definition.getLabel() + "\" is a built-in collection and can't be deleted");
        }
        itemService.getObject().dropStorage(definition.getStorage());
        repository.deleteById(key);
    }

    /** "Fabric Types" -> "fabricTypes", matching the camelCase shape of the built-in keys. */
    private String deriveKey(String label) {
        String[] words = label.trim().split("[^A-Za-z0-9]+");
        StringBuilder key = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (key.isEmpty()) {
                key.append(word.toLowerCase(Locale.ROOT));
            } else {
                key.append(word.substring(0, 1).toUpperCase(Locale.ROOT))
                        .append(word.substring(1).toLowerCase(Locale.ROOT));
            }
        }
        // A leading digit would break the key pattern the API validates on the way in.
        if (!key.isEmpty() && Character.isDigit(key.charAt(0))) {
            key.insert(0, "c");
        }
        return key.toString();
    }
}
