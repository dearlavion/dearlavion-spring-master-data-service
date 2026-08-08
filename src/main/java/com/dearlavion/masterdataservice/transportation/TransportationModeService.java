package com.dearlavion.masterdataservice.transportation;

import com.dearlavion.masterdataservice.transportation.model.TransportationMode;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class TransportationModeService extends AbstractReferenceItemService<TransportationMode> {
    public TransportationModeService(TransportationModeRepository repository) {
        super(repository, TransportationMode::new, "TransportationMode");
    }
}
