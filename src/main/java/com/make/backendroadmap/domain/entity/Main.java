package com.make.backendroadmap.domain.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum Main {
    INTERNET(1),
    BASIC_FE(2),
    OS(3),
    LANGUAGE(4),
    Algorithm(5),
    GIT(6),
    REPO_SERVICE(7),
    RDB(8),
    NOSQL(9),
    DB_KNOWLEDGE(10),
    API(11),
    FRAMEWORK(12),
    CACHING(13),
    WEB_SECURITY(14),
    TEST(15),
    CI_CD(16),
    DESIGN_PATTERN(17),
    SEARCH_ENGINE(18),
    CONTAINER(19),
    WEB_SERVER(20);

    private final int mainDocsOrder;

    Main(int mainDocsOrder) {
        this.mainDocsOrder = mainDocsOrder;
    }

    public int getMainDocsOrder() {
        return mainDocsOrder;
    }

    public static List<Main> getOrderedMainDocs() {
        return EnumSet.allOf(Main.class).stream()
                .sorted(Comparator.comparingInt(Main::getMainDocsOrder))
                .collect(Collectors.toList());
    }
    

}
