package com.dearlavion.masterdataservice.seed;

import com.dearlavion.masterdataservice.activity.ActivityService;
import com.dearlavion.masterdataservice.destination.DestinationService;
import com.dearlavion.masterdataservice.duration.DurationService;
import com.dearlavion.masterdataservice.gender.GenderService;
import com.dearlavion.masterdataservice.kitcategory.KitCategoryService;
import com.dearlavion.masterdataservice.party.PartyService;
import com.dearlavion.masterdataservice.season.SeasonService;
import com.dearlavion.masterdataservice.transportation.TransportationModeService;
import com.dearlavion.masterdataservice.kitsettings.KitSettingsService;
import com.dearlavion.masterdataservice.kitsettings.model.KitSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Idempotently upserts all 8 reference-data types + the default question order from
 * {@link ReferenceDataSeed} — byte-identical to store-engine-v2's TaxonomySeedData, so a fresh
 * deploy of this service behaves the same as the live taxonomy module until an admin edits
 * something. Only runs under the "seed" Spring profile:
 * {@code ./mvnw spring-boot:run -Dspring-boot.run.profiles=seed}.
 */
@Slf4j
@Component
@Profile("seed")
@RequiredArgsConstructor
public class SeedRunner implements CommandLineRunner {

    private final DestinationService destinationService;
    private final SeasonService seasonService;
    private final PartyService partyService;
    private final TransportationModeService transportationModeService;
    private final ActivityService activityService;
    private final KitCategoryService kitCategoryService;
    private final DurationService durationService;
    private final GenderService genderService;
    private final KitSettingsService kitSettingsService;

    @Override
    public void run(String... args) {
        int count = 0;
        for (ReferenceDataSeed.Entry entry : ReferenceDataSeed.ENTRIES) {
            switch (entry.type()) {
                case "destination" -> destinationService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "season" -> seasonService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "party" -> partyService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "transportation" -> transportationModeService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "activity" -> activityService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "kitCategory" -> kitCategoryService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                case "gender" -> genderService.upsert(entry.value(), entry.order(), entry.emoji(), entry.subtext());
                default -> throw new IllegalStateException("Unknown seed type: " + entry.type());
            }
            count++;
        }

        for (ReferenceDataSeed.DurationEntry entry : ReferenceDataSeed.DURATION_ENTRIES) {
            durationService.upsertWithCode(entry.value(), entry.order(), entry.subtext(), entry.code());
            count++;
        }

        kitSettingsService.updateOrder(KitSettings.DEFAULT_ORDER);

        log.info("Seeded {} reference-data values across 8 types + default question order.", count);
    }
}
