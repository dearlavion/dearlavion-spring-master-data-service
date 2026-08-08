package com.dearlavion.masterdataservice.typeorder;

import com.dearlavion.masterdataservice.typeorder.request.UpdateTypeOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/type-order")
@RequiredArgsConstructor
public class AdminTypeOrderController {

    private final TypeOrderService service;

    @PutMapping
    public Map<String, List<String>> update(@Valid @RequestBody UpdateTypeOrderRequest body) {
        return Map.of("order", service.updateOrder(body.order()));
    }
}
