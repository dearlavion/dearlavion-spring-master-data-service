package com.dearlavion.masterdataservice.kitcategory;

import com.dearlavion.masterdataservice.kitcategory.model.KitCategory;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/kit-categories")
public class AdminKitCategoryController extends AbstractAdminReferenceItemController<KitCategory> {
    public AdminKitCategoryController(KitCategoryService service) {
        super(service);
    }
}
