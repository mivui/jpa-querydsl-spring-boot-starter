package com.github.uinios.jpa.basic.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jingle-Cat
 */
@Getter
@AllArgsConstructor
public enum PageEnum {
    PAGE_TOTAL("total"),
    PAGE_CONTENT("content");
    private final String value;
}
