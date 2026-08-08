package com.dearlavion.masterdataservice.destination;

import com.dearlavion.masterdataservice.destination.model.Destination;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/destinations")
public class DestinationController extends AbstractReferenceItemController<Destination> {
    public DestinationController(DestinationService service) {
        super(service);
    }
}
