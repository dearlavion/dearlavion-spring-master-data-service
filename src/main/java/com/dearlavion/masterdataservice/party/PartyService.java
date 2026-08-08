package com.dearlavion.masterdataservice.party;

import com.dearlavion.masterdataservice.party.model.Party;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class PartyService extends AbstractReferenceItemService<Party> {
    public PartyService(PartyRepository repository) {
        super(repository, Party::new, "Party");
    }
}
