package com.dearlavion.masterdataservice.kitsettings;

import com.dearlavion.masterdataservice.kitsettings.model.KitSettings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KitSettingsRepository extends MongoRepository<KitSettings, String> {
}
