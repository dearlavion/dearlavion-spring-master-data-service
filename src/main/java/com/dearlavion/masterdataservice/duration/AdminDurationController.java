package com.dearlavion.masterdataservice.duration;

import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import com.dearlavion.masterdataservice.common.exception.ConflictException;
import com.dearlavion.masterdataservice.common.request.CreateReferenceItemRequest;
import com.dearlavion.masterdataservice.duration.model.Duration;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Duration has a fixed set of options (day/short/medium/long) — add/delete are rejected here with
 * the same runtime-rejection UX as today's taxonomy module (a 409, not a compile-time 404/405).
 * Only renaming/reordering an existing row (inherited update()) is allowed.
 */
@RestController
@RequestMapping("/admin/durations")
public class AdminDurationController extends AbstractAdminReferenceItemController<Duration> {

    public AdminDurationController(DurationService service) {
        super(service);
    }

    @Override
    @PostMapping
    public Duration create(@Valid @RequestBody CreateReferenceItemRequest body) {
        throw new ConflictException("Duration has a fixed set of options — edit the existing ones instead of adding new ones");
    }

    @Override
    @DeleteMapping("/{id}")
    public void remove(@PathVariable String id) {
        throw new ConflictException("Duration has a fixed set of options and cannot be deleted");
    }
}
