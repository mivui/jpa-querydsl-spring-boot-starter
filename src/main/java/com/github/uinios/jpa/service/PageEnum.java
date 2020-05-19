package com.github.uinios.jpa.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PageEnum {
    PAGE_TOTAL("total"),
    PAGE_CONTENT("content");
    private final String value;
}
