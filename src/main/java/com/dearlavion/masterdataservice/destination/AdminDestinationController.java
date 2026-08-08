package com.dearlavion.masterdataservice.destination;

import com.dearlavion.masterdataservice.destination.model.Destination;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/destinations")
public class AdminDestinationController extends AbstractAdminReferenceItemController<Destination> {
    public AdminDestinationController(DestinationService service) {
        super(service);
    }
}
