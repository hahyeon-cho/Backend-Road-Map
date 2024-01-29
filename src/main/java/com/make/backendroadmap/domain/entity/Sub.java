package com.make.backendroadmap.domain.entity;

public enum Sub {
    IP("1", 1),
    TCP_UDP("1", 2),
    PORT("1", 3),
    DNS("1", 4),
    HTTP("1", 5),
    COOKIE("1", 6),
    CACHE("1", 7);

    private final String category;

    private final int subDocsOrder;

    Sub(String category, int subDocsOrder) {
        this.category = category;
        this.subDocsOrder = subDocsOrder;
    }

    public String getCategory() {
        return this.category;
    }
}
