package com.dearlavion.masterdataservice.typeorder.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateTypeOrderRequest(@NotEmpty List<String> order) {
}
