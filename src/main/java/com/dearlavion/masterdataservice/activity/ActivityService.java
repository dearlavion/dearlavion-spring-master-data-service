package com.dearlavion.masterdataservice.activity;

import com.dearlavion.masterdataservice.activity.model.Activity;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class ActivityService extends AbstractReferenceItemService<Activity> {
    public ActivityService(ActivityRepository repository) {
        super(repository, Activity::new, "Activity");
    }
}
