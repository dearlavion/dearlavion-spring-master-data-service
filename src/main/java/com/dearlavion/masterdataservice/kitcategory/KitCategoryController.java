package com.dearlavion.masterdataservice.kitcategory;

import com.dearlavion.masterdataservice.kitcategory.model.KitCategory;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kit-categories")
public class KitCategoryController extends AbstractReferenceItemController<KitCategory> {
    public KitCategoryController(KitCategoryService service) {
        super(service);
    }
}
