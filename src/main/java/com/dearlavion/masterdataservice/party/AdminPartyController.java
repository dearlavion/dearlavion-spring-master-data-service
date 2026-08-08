package com.dearlavion.masterdataservice.party;

import com.dearlavion.masterdataservice.party.model.Party;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/parties")
public class AdminPartyController extends AbstractAdminReferenceItemController<Party> {
    public AdminPartyController(PartyService service) {
        super(service);
    }
}
