package com.dearlavion.masterdataservice.typeorder;

import com.dearlavion.masterdataservice.typeorder.model.TypeOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TypeOrderService {

    private static final Set<String> VALID_TYPES = Set.of(
            "destination", "season", "party", "transportation", "activity", "kitCategory", "duration", "gender"
    );

    private final TypeOrderRepository repository;

    public List<String> getOrder() {
        return repository.findById(TypeOrder.SINGLETON_ID)
                .map(TypeOrder::getOrder)
                .filter(order -> !order.isEmpty())
                .orElse(TypeOrder.DEFAULT_ORDER);
    }

    public List<String> updateOrder(List<String> order) {
        for (String type : order) {
            if (!VALID_TYPES.contains(type)) {
                throw new IllegalArgumentException("Unknown type: " + type);
            }
        }
        TypeOrder typeOrder = repository.findById(TypeOrder.SINGLETON_ID).orElseGet(TypeOrder::new);
        typeOrder.setOrder(order);
        repository.save(typeOrder);
        return order;
    }
}
