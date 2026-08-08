package com.dearlavion.masterdataservice.activity;

import com.dearlavion.masterdataservice.activity.model.Activity;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/activities")
public class AdminActivityController extends AbstractAdminReferenceItemController<Activity> {
    public AdminActivityController(ActivityService service) {
        super(service);
    }
}
