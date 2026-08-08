package com.dearlavion.masterdataservice.gender;

import com.dearlavion.masterdataservice.gender.model.Gender;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class GenderService extends AbstractReferenceItemService<Gender> {
    public GenderService(GenderRepository repository) {
        super(repository, Gender::new, "Gender");
    }
}
