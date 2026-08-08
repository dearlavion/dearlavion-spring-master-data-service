package com.dearlavion.masterdataservice.season;

import com.dearlavion.masterdataservice.season.model.Season;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemService;
import org.springframework.stereotype.Service;

@Service
public class SeasonService extends AbstractReferenceItemService<Season> {
    public SeasonService(SeasonRepository repository) {
        super(repository, Season::new, "Season");
    }
}
