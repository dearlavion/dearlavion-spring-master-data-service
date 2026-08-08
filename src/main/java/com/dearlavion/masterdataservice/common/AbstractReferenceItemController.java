package com.dearlavion.masterdataservice.common;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Base for the public read controller of a single reference-data type. Concrete subclasses just
 * add {@code @RestController @RequestMapping("/xxx")} and a constructor forwarding their service.
 */
public abstract class AbstractReferenceItemController<T extends ReferenceItem> {

    protected final AbstractReferenceItemService<T> service;

    protected AbstractReferenceItemController(AbstractReferenceItemService<T> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> list() {
        return service.listAll();
    }
}
