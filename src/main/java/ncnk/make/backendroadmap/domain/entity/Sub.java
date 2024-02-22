package ncnk.make.backendroadmap.domain.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Sub {
    IP(1, 1),
    TCP_UDP(1, 2),
    PORT(1, 3),
    DNS(1, 4),
    HTTP(1, 5),
    COOKIE(1, 6),
    CACHE(1, 7),
    HTML(2, 1),
    CSS(2, 2),
    BASIC_JS(2, 3),
    OS_WORK(3, 1),
    PROCESS(3, 2),
    THREADS_CONCURRENTCY(3, 3),
    BASIC_TERMAIN_COMMANDS(3, 4),
    MEMORY_MANAGE(3, 5),
    IPC(3, 6),
    INPUT_OUTPUT(3, 7),
    BASIC_NETWORK(3, 8),
    JAVA(4, 1),
    C_SHAP(4, 2),
    PYTHON(4, 3),
    PHP(4, 4),
    RUBY(4, 5),
    GO(4, 6),
    RUST(4, 7),
    JAVASCRIPT(4, 8),
    BUBBLE_SORT(5, 1),
    SELECTION_SORT(5, 2),
    INSERTION_SORT(5, 3),
    QUICK_SORT(5, 4),
    MERGE_SORT(5, 5),
    HEAP_SORT(5, 6),
    RADIX_SORT(5, 7),
    COUNTING_SORT(5, 8),
    BINARY_SEARCH(5, 9),
    HASH_TABLE(5, 10),
    DFS_BFS(5, 11),
    LIS(5, 12),
    LCA(5, 13),
    DP(5, 14),
    DIJKSTRA(5, 15),
    BITMASK(5, 16),
    USING_GIT(6, 1),
    GITHUB(7, 1),
    GITLAB(7, 2),
    POSTGRESQL(8, 1),
    MYSQL(8, 2),
    ORACLE(8, 3),
    DYNAMODB(9, 1),
    TRANSACTION(10, 1),
    ACID(10, 2),
    ORM(10, 3),
    N_PLUS_ONE(10, 4),
    AUTHENTICATION(11, 1),
    REST(11, 2),
    JSON(11, 3),
    API(11, 4),
    SOAP(11, 5),
    SPRING(12, 1),
    DJANGO(12, 2),
    CDN(13, 1),
    SERVER_SIDE(13, 2),
    CLIENT_SIDE(13, 3),
    HTTPS(14, 1),
    CORS(14, 2),
    SSL_TLS(14, 3),
    INTEGRATION_TEST(15, 1),
    UNIT_TEST(15, 2),
    FUNCTIONAL_TEST(15, 3),
    JENKINS(16, 1),
    DESIGN_PATTERN(17, 1),
    GOF_DESIGN_PATTERN(17, 2),
    DOMAIN_DRIVEN_DESIGN(17, 3),
    TEST_DRIVEN_DESIGN(17, 4),
    SOLID(17, 5),
    ELASTIC_SEARCH(18, 1),
    DOCKER(19, 1),
    WEB_SERVER(20, 1),
    NGINX(20, 2);

    private final int category;

    private final int subDocsOrder;


    Sub(int category, int subDocsOrder) {
        this.category = category;
        this.subDocsOrder = subDocsOrder;
    }

    public int getCategory() {
        return this.category;
    }

    public int getSubDocsOrder() {
        return subDocsOrder;
    }


    public static List<Sub> getOrderedSubDocsInCategory(int category) {
        return Arrays.stream(Sub.values())
                .filter(sub -> sub.getCategory() == category)
                .sorted(Comparator.comparingInt(Sub::getSubDocsOrder))
                .collect(Collectors.toList());
    }
}