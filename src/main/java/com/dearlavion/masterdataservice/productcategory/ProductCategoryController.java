package com.dearlavion.masterdataservice.productcategory;

import com.dearlavion.masterdataservice.productcategory.model.ProductCategory;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController extends AbstractReferenceItemController<ProductCategory> {
    public ProductCategoryController(ProductCategoryService service) {
        super(service);
    }
}
