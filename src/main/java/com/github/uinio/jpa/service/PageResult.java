package com.github.uinio.jpa.service;

/**
 * @author LvXiaoBu
 */
public enum PageResult {

    PAGE_TOTAL("total"),
    PAGE_CONTENT("content");

    private final String value;

    PageResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String total() {
        return PAGE_TOTAL.getValue();
    }

    public static String content() {
        return PAGE_CONTENT.getValue();
    }
}
