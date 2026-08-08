package com.dearlavion.masterdataservice.duration;

import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import com.dearlavion.masterdataservice.duration.model.Duration;
import org.springframework.stereotype.Service;

@Service
public class DurationService extends AbstractReferenceItemService<Duration> {

    public DurationService(DurationRepository repository) {
        super(repository, Duration::new, "Duration");
    }

    /** Idempotent upsert by value, additionally setting the stable {@code code} — used only by
     * SeedRunner (the API never exposes {@code code}, see Duration.java). */
    public void upsertWithCode(String value, int order, String subtext, String code) {
        Duration existing = repository.findByValue(value).orElseGet(Duration::new);
        existing.setValue(value);
        existing.setOrder(order);
        existing.setSubtext(subtext);
        existing.setCode(code);
        repository.save(existing);
    }
}
