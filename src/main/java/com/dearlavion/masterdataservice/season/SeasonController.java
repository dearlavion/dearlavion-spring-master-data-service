package com.dearlavion.masterdataservice.season;

import com.dearlavion.masterdataservice.season.model.Season;
import com.dearlavion.masterdataservice.common.AbstractReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seasons")
public class SeasonController extends AbstractReferenceItemController<Season> {
    public SeasonController(SeasonService service) {
        super(service);
    }
}
