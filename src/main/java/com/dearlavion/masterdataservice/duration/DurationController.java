package com.dearlavion.masterdataservice.duration;

import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import com.dearlavion.masterdataservice.duration.model.Duration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/durations")
public class DurationController extends AbstractReferenceItemController<Duration> {
    public DurationController(DurationService service) {
        super(service);
    }
}
