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
    private final CustomCollectionItemRepository itemRepository;

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
        return repository.save(new CollectionDefinition(key, label, CollectionDefinition.customPath(key), false));
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
        itemRepository.deleteByCollectionKey(key);
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
