package com.dearlavion.masterdataservice.gender;

import com.dearlavion.masterdataservice.gender.model.Gender;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genders")
public class GenderController extends AbstractReferenceItemController<Gender> {
    public GenderController(GenderService service) {
        super(service);
    }
}
