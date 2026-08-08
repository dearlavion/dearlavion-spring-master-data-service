package com.dearlavion.masterdataservice.transportation;

import com.dearlavion.masterdataservice.transportation.model.TransportationMode;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transportation-modes")
public class TransportationModeController extends AbstractReferenceItemController<TransportationMode> {
    public TransportationModeController(TransportationModeService service) {
        super(service);
    }
}
