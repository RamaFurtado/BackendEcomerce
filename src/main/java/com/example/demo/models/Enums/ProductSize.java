package com.example.demo.models.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductSize {
    S, M, L, XL, XXL,
    _38("38"), _39("39"), _40("40"), _41("41"), _42("42"), _43("43"), _44("44"), _45("45");

    private String value;

    ProductSize() {
        this.value = name();
    }

    ProductSize(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ProductSize fromValue(String value) {
        for (ProductSize size : ProductSize.values()) {
            if (size.value.equals(value)) {
                return size;
            }
        }
        throw new IllegalArgumentException("Invalid ProductSize: " + value);
    }
}

