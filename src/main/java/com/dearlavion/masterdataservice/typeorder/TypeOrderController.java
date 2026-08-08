package com.dearlavion.masterdataservice.typeorder;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TypeOrderController {

    private final TypeOrderService service;

    @GetMapping("/type-order")
    public Map<String, List<String>> getOrder() {
        return Map.of("order", service.getOrder());
    }
}
