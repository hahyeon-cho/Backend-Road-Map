package ncnk.make.backendroadmap.domain.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

@Getter
public enum Main {
    INTERNET("Internet", 1, "http://localhost:8080/internet"),
    BASIC_FE("Basic FE", 2, "http://localhost:8080/fe"),
    OS("OS", 3, "http://localhost:8080/os"),
    LANGUAGE("Language", 4, "http://localhost:8080/language"),
    ALGORITHM("Algorithm", 5, "http://localhost:8080/algorithm"),
    GIT("Using Git", 6, "http://localhost:8080/git"),
    REPO_SERVICE("Repo hosting Services", 7, "http://localhost:8080/git/repo"),
    RDB("Relational DB", 8, "http://localhost:8080/rdb"),
    NOSQL("NoSQL DB", 9, "http://localhost:8080/nosql"),
    DB_KNOWLEDGE("DB Knowledge", 10, "http://localhost:8080/db/knowledge"),
    API("APIs", 11, "http://localhost:8080/apis"),
    FRAMEWORK("Framework", 12, "http://localhost:8080/framework"),
    CACHING("Caching", 13, "http://localhost:8080/caching"),
    WEB_SECURITY("Web Security", 14, "http://localhost:8080/security"),
    TEST("Test", 15, "http://localhost:8080/test"),
    CI_CD("CI / CD", 16, "http://localhost:8080/ci/cd"),
    DESIGN_PATTERN("Design Pattern", 17, "http://localhost:8080/design/pattern"),
    SEARCH_ENGINE("Search Engines", 18, "http://localhost:8080/search/engines"),
    CONTAINER("Container", 19, "http://localhost:8080/container"),
    WEB_SERVER("Web Server", 20, "http://localhost:8080/server");


    private final String mainCategory;
    private final int mainDocsOrder;
    private final String url;

    Main(String mainCategory, int mainDocsOrder, String url) {
        this.mainCategory = mainCategory;
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