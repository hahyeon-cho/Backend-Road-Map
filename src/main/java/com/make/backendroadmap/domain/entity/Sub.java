package com.make.backendroadmap.domain.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Sub {
    IP(1, 1, "url"),
    TCP_UDP(1, 2, "url"),
    PORT(1, 3, "url"),
    DNS(1, 4, "url"),
    HTTP(1, 5, "url"),
    COOKIE(1, 6, "url"),
    CACHE(1, 7, "url"),
    HTML(2, 1, "url"),
    CSS(2, 2, "url"),
    BASIC_JS(2, 3, "url"),
    OS_WORK(3, 1, "url"),
    PROCESS(3, 2, "url"),
    THREADS_CONCURRENTCY(3, 3, "url"),
    BASIC_TERMAIN_COMMANDS(3, 4, "url"),
    MEMORY_MANAGE(3, 5, "url"),
    IPC(3, 6, "url"),
    INPUT_OUTPUT(3, 7, "url"),
    BASIC_NETWORK(3, 8, "url"),
    JAVA(4, 1, "url"),
    C_SHAP(4, 2, "url"),
    PYTHON(4, 3, "url"),
    PHP(4, 4, "url"),
    RUBY(4, 5, "url"),
    GO(4, 6, "url"),
    RUST(4, 7, "url"),
    JAVASCRIPT(4, 8, "url"),
    BUBBLE_SORT(5, 1, "url"),
    SELECTION_SORT(5, 2, "url"),
    INSERTION_SORT(5, 3, "url"),
    QUICK_SORT(5, 4, "url"),
    MERGE_SORT(5, 5, "url"),
    HEAP_SORT(5, 6, "url"),
    RADIX_SORT(5, 7, "url"),
    COUNTING_SORT(5, 8, "url"),
    BINARY_SEARCH(5, 9, "url"),
    HASH_TABLE(5, 10, "url"),
    DFS_BFS(5, 11, "url"),
    LIS(5, 12, "url"),
    LCA(5, 13, "url"),
    DP(5, 14, "url"),
    DIJKSTRA(5, 15, "url"),
    BITMASK(5, 16, "url"),
    USING_GIT(6, 1, "url"),
    GITHUB(7, 1, "url"),
    GITLAB(7, 2, "url"),
    POSTGRESQL(8, 1, "url"),
    MYSQL(8, 2, "url"),
    ORACLE(8, 3, "url"),
    DYNAMODB(9, 1, "url"),
    TRANSACTION(10, 1, "url"),
    ACID(10, 2, "url"),
    ORM(10, 3, "url"),
    N_PLUS_ONE(10, 4, "url"),
    AUTHENTICATION(11, 1, "url"),
    REST(11, 2, "url"),
    JSON(11, 3, "url"),
    API(11, 4, "url"),
    SOAP(11, 5, "url"),
    SPRING(12, 1, "url"),
    DJANGO(12, 2, "url"),
    CDN(13, 1, "url"),
    SERVER_SIDE(13, 2, "url"),
    CLIENT_SIDE(13, 3, "url"),
    HTTPS(14, 1, "url"),
    CORS(14, 2, "url"),
    SSL_TLS(14, 3, "url"),
    INTEGRATION_TEST(15, 1, "url"),
    UNIT_TEST(15, 2, "url"),
    FUNCTIONAL_TEST(15, 3, "url"),
    JENKINS(16, 1, "url"),
    DESIGN_PATTERN(17, 1, "url"),
    GOF_DESIGN_PATTERN(17, 2, "url"),
    DOMAIN_DRIVEN_DESIGN(17, 3, "url"),
    TEST_DRIVEN_DESIGN(17, 4, "url"),
    SOLID(17, 5, "url"),
    ELASTIC_SEARCH(18, 1, "url"),
    DOCKER(19, 1, "url"),
    WEB_SERVER(20, 1, "url"),
    NGINX(20, 2, "url");

    private final int category;

    private final int subDocsOrder;

    private final String url;

    Sub(int category, int subDocsOrder, String url) {
        this.category = category;
        this.subDocsOrder = subDocsOrder;
        this.url = url;
    }

    public int getCategory() {
        return this.category;
    }

    public int getSubDocsOrder() {
        return subDocsOrder;
    }

    public String getUrl() {
        return url;
    }

    public static List<Sub> getOrderedSubDocsInCategory(int category) {
        return Arrays.stream(Sub.values())
                .filter(sub -> sub.getCategory() == category)
                .sorted(Comparator.comparingInt(Sub::getSubDocsOrder))
                .collect(Collectors.toList());
    }

    public static List<String> getUrlSubDocsInCategory(int category) {
        return Arrays.stream(Sub.values())
                .filter(sub -> sub.getCategory() == category)
                .sorted(Comparator.comparingInt(Sub::getSubDocsOrder))
                .map(Sub::getUrl)
                .collect(Collectors.toList());
    }
}
