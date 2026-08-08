package com.dearlavion.masterdataservice.gender;

import com.dearlavion.masterdataservice.gender.model.Gender;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/genders")
public class AdminGenderController extends AbstractAdminReferenceItemController<Gender> {
    public AdminGenderController(GenderService service) {
        super(service);
    }
}
