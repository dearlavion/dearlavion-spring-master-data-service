package com.dearlavion.masterdataservice.productcategory;

import com.dearlavion.masterdataservice.productcategory.model.ProductCategory;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product-categories")
public class AdminProductCategoryController extends AbstractAdminReferenceItemController<ProductCategory> {
    public AdminProductCategoryController(ProductCategoryService service) {
        super(service);
    }
}
