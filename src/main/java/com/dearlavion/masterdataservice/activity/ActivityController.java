package com.dearlavion.masterdataservice.activity;

import com.dearlavion.masterdataservice.activity.model.Activity;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController extends AbstractReferenceItemController<Activity> {
    public ActivityController(ActivityService service) {
        super(service);
    }
}
