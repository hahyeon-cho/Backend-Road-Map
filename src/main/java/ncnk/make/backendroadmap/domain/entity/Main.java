package ncnk.make.backendroadmap.domain.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

@Getter
public enum Main {
    INTERNET(1, "http://localhost:8080/internet"),
    BASIC_FE(2, "http://localhost:8080/fe"),
    OS(3, "http://localhost:8080/os"),
    LANGUAGE(4, "http://localhost:8080/language"),
    ALGORITHM(5, "http://localhost:8080/algorithm"),
    GIT(6, "http://localhost:8080/git"),
    REPO_SERVICE(7, "http://localhost:8080/git/repo"),
    RDB(8, "http://localhost:8080/rdb"),
    NOSQL(9, "http://localhost:8080/nosql"),
    DB_KNOWLEDGE(10, "http://localhost:8080/db/knowledge"),
    API(11, "http://localhost:8080/apis"),
    FRAMEWORK(12, "http://localhost:8080/framework"),
    CACHING(13, "http://localhost:8080/caching"),
    WEB_SECURITY(14, "http://localhost:8080/security"),
    TEST(15, "http://localhost:8080/test"),
    CI_CD(16, "http://localhost:8080/ci/cd"),
    DESIGN_PATTERN(17, "http://localhost:8080/design/pattern"),
    SEARCH_ENGINE(18, "http://localhost:8080/search/engines"),
    CONTAINER(19, "http://localhost:8080/container"),
    WEB_SERVER(20, "http://localhost:8080/server");

    private final int mainDocsOrder;

    private final String url;

    Main(int mainDocsOrder, String url) {
        this.mainDocsOrder = mainDocsOrder;
        this.url = url;
    }

    public static int getMaximumOrder() {
        int max = 0;
        for (Main main : Main.values()) {
            if (main.getMainDocsOrder() > max) {
                max = main.getMainDocsOrder();
            }
        }
        return max;
    }

    public static Main getInstance(String mainDoc) {
        for (Main main : Main.values()) {
            if (main.toString().equals(mainDoc)) {
                return main;
            }
        }
        throw new ResourceNotFoundException();
    }


    public static Main getEnumByMainDocsOrder(int mainDocsOrder) {
        for (Main main : Main.values()) {
            if (main.getMainDocsOrder() == mainDocsOrder) {
                return main;
            }
        }
        throw new ResourceNotFoundException();
    }

    public static List<Main> getOrderedMainDocs() {
        return EnumSet.allOf(Main.class).stream()
                .sorted(Comparator.comparingInt(Main::getMainDocsOrder))
                .collect(Collectors.toList());
    }

    public static Main findMainBySearchQuery(int searchQuery) {
        for (Main main : Main.values()) {
            if (main.getMainDocsOrder() == searchQuery) {
                return main;
            }
        }
        throw new ResourceNotFoundException();
    }
}