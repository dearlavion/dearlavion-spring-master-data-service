package com.dearlavion.masterdataservice.season;

import com.dearlavion.masterdataservice.season.model.Season;
import com.dearlavion.masterdataservice.common.AbstractAdminReferenceItemController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/seasons")
public class AdminSeasonController extends AbstractAdminReferenceItemController<Season> {
    public AdminSeasonController(SeasonService service) {
        super(service);
    }
}
