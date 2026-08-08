package com.dearlavion.masterdataservice.common.exception;

public record ApiError(int statusCode, Object message, String error) {
}
