package com.dearlavion.masterdataservice.common.request;

import jakarta.validation.constraints.NotBlank;

public record CreateReferenceItemRequest(@NotBlank String value, Integer order, String emoji, String subtext) {
}
