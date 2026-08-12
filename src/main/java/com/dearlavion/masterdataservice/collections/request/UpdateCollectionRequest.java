package com.dearlavion.masterdataservice.collections.request;

import jakarta.validation.constraints.NotBlank;

/** Only the label is editable — the key is baked into URLs and into
 * store-engine's kit_settings, so it never changes. */
public record UpdateCollectionRequest(@NotBlank String label) {
}
