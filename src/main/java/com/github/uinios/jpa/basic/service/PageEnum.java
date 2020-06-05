package com.github.uinios.jpa.basic.service;

/**
 * @author Jingle-Cat
 */
public enum PageEnum {
    PAGE_TOTAL("total"),
    PAGE_CONTENT("content");
    private final String value;

    PageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
