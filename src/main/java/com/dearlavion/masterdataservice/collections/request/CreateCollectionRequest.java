package com.dearlavion.masterdataservice.collections.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @param key optional — derived from the label when omitted. Constrained to the same camelCase
 *            shape the 8 built-in keys use, since it appears in URLs and in store-engine's kit_settings.
 */
public record CreateCollectionRequest(
        @NotBlank String label,
        @Pattern(regexp = "^[a-z][a-zA-Z0-9]{0,39}$", message = "must be camelCase, starting with a lowercase letter")
        String key
) {
}
