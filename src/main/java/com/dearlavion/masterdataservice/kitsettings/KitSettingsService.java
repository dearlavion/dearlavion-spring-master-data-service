package com.dearlavion.masterdataservice.kitsettings;

import com.dearlavion.masterdataservice.collections.CollectionRegistryService;
import com.dearlavion.masterdataservice.kitsettings.model.KitSettings;
import com.dearlavion.masterdataservice.kitsettings.model.SectionSettings;
import com.dearlavion.masterdataservice.kitsettings.request.UpdateKitSettingsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KitSettingsService {

    private final KitSettingsRepository repository;

    /**
     * Valid keys come from the live collections registry rather than a hardcoded set of 8, so an
     * admin-created collection can take its place in the survey like any built-in.
     */
    private final CollectionRegistryService collectionRegistryService;

    public KitSettings get() {
        return repository.findById(KitSettings.SINGLETON_ID).orElseGet(KitSettings::new);
    }

    public KitSettings update(UpdateKitSettingsRequest patch) {
        KitSettings settings = get();

        if (patch.order() != null) {
            Set<String> validKeys = collectionRegistryService.allKeys();
            for (String key : patch.order()) {
                if (!validKeys.contains(key)) {
                    throw new IllegalArgumentException("Unknown collection: " + key);
                }
            }
            settings.setOrder(patch.order());
        }

        if (patch.sections() != null) {
            Set<String> validKeys = collectionRegistryService.allKeys();
            // Merge rather than replace, so a client saving one section's behaviour can't silently
            // drop the rest.
            Map<String, SectionSettings> merged = new LinkedHashMap<>(settings.getSections());
            for (Map.Entry<String, SectionSettings> entry : patch.sections().entrySet()) {
                if (!validKeys.contains(entry.getKey())) {
                    throw new IllegalArgumentException("Unknown collection: " + entry.getKey());
                }
                merged.put(entry.getKey(), entry.getValue());
            }
            settings.setSections(merged);
        }

        settings.setId(KitSettings.SINGLETON_ID);
        return repository.save(settings);
    }

    /** Kept for SeedRunner, which restores the out-of-the-box order after seeding values. */
    public List<String> updateOrder(List<String> order) {
        return update(new UpdateKitSettingsRequest(order, null)).getOrder();
    }
}
