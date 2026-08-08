package com.dearlavion.masterdataservice.destination;

import com.dearlavion.masterdataservice.destination.model.Destination;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class DestinationService extends AbstractReferenceItemService<Destination> {
    public DestinationService(DestinationRepository repository) {
        super(repository, Destination::new, "Destination");
    }
}
