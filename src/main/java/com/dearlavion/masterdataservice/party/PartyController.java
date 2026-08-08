package com.dearlavion.masterdataservice.party;

import com.dearlavion.masterdataservice.party.model.Party;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parties")
public class PartyController extends AbstractReferenceItemController<Party> {
    public PartyController(PartyService service) {
        super(service);
    }
}
