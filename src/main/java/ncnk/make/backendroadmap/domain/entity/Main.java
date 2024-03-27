package ncnk.make.backendroadmap.domain.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;

/**
 * 대분류 정보 (대분류 이름, 대분류 순서, 저장 URL)
 */
@Getter
public enum Main {
    INTERNET("Internet", 1, "http://localhost:8080/roadmap/sub/1"),
    BASIC_FE("Basic FE", 2, "http://localhost:8080/roadmap/sub/2"),
    OS("OS", 3, "http://localhost:8080/roadmap/sub/3"),
    LANGUAGE("Language", 4, "http://localhost:8080/roadmap/sub/4"),
    ALGORITHM("Algorithm", 5, "http://localhost:8080/roadmap/sub/5"),
    GIT("Using Git", 6, "http://localhost:8080/roadmap/sub/6"),
    REPO_SERVICE("Repo hosting Services", 7, "http://localhost:8080/roadmap/sub/7"),
    RDB("Relational DB", 8, "http://localhost:8080/roadmap/sub/8"),
    NOSQL("NoSQL DB", 9, "http://localhost:8080/roadmap/sub/9"),
    DB_KNOWLEDGE("DB Knowledge", 10, "http://localhost:8080/roadmap/sub/10"),
    API("APIs", 11, "http://localhost:8080/roadmap/sub/11"),
    FRAMEWORK("Framework", 12, "http://localhost:8080/roadmap/sub/12"),
    CACHING("Caching", 13, "http://localhost:8080/roadmap/sub/13"),
    WEB_SECURITY("Web Security", 14, "http://localhost:8080/roadmap/sub/14"),
    TEST("Test", 15, "http://localhost:8080/roadmap/sub/15"),
    CI_CD("CI / CD", 16, "http://localhost:8080/roadmap/sub/16"),
    DESIGN_PATTERN("Design Pattern", 17, "http://localhost:8080/roadmap/sub/17"),
    SEARCH_ENGINE("Search Engines", 18, "http://localhost:8080/roadmap/sub/18"),
    CONTAINER("Container", 19, "http://localhost:8080/roadmap/sub/19"),
    WEB_SERVER("Web Server", 20, "http://localhost:8080/roadmap/sub/20");


    private final String mainCategory;
    private final int mainDocsOrder;
    private final String url;

    //생성자
    Main(String mainCategory, int mainDocsOrder, String url) {
        this.mainCategory = mainCategory;
        this.mainDocsOrder = mainDocsOrder;
        this.url = url;
    }

    // 대분류 중 마지막 순서의 대분류 순서값 얻기
    public static int getMaximumOrder() {
        int max = 0;
        for (Main main : Main.values()) {
            if (main.getMainDocsOrder() > max) {
                max = main.getMainDocsOrder();
            }
        }
        return max;
    }

    // 인자 값(mainDoc)와 같은 대분류 정보 얻기
    public static Main getInstance(String mainDoc) {
        for (Main main : Main.values()) {
            if (main.toString().equals(mainDoc)) {
                return main;
            }
        }
        throw new ResourceNotFoundException();
    }


    // 인자 값(mainDocsOrder)와 같은 대분류 정보 얻기
    public static Main getEnumByMainDocsOrder(int mainDocsOrder) {
        for (Main main : Main.values()) {
            if (main.getMainDocsOrder() == mainDocsOrder) {
                return main;
            }
        }
        throw new ResourceNotFoundException();
    }


    // 대분류 순서대로 정렬해서 List로 반환
    public static List<Main> getOrderedMainDocs() {
        return EnumSet.allOf(Main.class).stream()
                .sorted(Comparator.comparingInt(Main::getMainDocsOrder))
                .collect(Collectors.toList());
    }
}
