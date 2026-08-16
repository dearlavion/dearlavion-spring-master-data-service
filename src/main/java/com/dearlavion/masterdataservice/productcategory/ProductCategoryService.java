package com.dearlavion.masterdataservice.productcategory;

import com.dearlavion.masterdataservice.productcategory.model.ProductCategory;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService extends AbstractReferenceItemService<ProductCategory> {
    public ProductCategoryService(ProductCategoryRepository repository) {
        super(repository, ProductCategory::new, "ProductCategory");
    }
}
