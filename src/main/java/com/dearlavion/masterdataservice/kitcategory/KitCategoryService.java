package com.dearlavion.masterdataservice.kitcategory;

import com.dearlavion.masterdataservice.kitcategory.model.KitCategory;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class KitCategoryService extends AbstractReferenceItemService<KitCategory> {
    public KitCategoryService(KitCategoryRepository repository) {
        super(repository, KitCategory::new, "KitCategory");
    }
}
