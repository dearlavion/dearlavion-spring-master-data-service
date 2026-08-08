package com.dearlavion.masterdataservice.transportation;

import com.dearlavion.masterdataservice.transportation.model.TransportationMode;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/transportation-modes")
public class AdminTransportationModeController extends AbstractAdminReferenceItemController<TransportationMode> {
    public AdminTransportationModeController(TransportationModeService service) {
        super(service);
    }
}
