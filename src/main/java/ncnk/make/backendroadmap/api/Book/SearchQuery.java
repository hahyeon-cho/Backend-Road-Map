package ncnk.make.backendroadmap.api.Book;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public enum SearchQuery {
    internet(1, "네트워크", 351),
    BASIC_FE(2, "HTML", 351),
    OS(3, "운영체제", 351),
    JAVA(4, "JAVA", 2502),
    C_SHARP(4, "C#", 6360),
    PYTHON(4, "Python", 6734),
    PHP(4, "PHP", 6351),
    RUBY(4, "Ruby", 15778),
    GO(4, "Go", 437),
    RUST(4, "Ruby", 15778),
    JS(4, "JS", 2502),
    ALGORITHM(5, "알고리즘", 351);

    private final int category;
    private final String query; // 검색어
    private final int field; // 분야

    SearchQuery(int category, String query, int field) {
        this.category = category;
        this.query = query;
        this.field = field;
    }

    public static List<SearchQuery> getSearchQueries() {
        List<SearchQuery> searchQueries = new ArrayList<>();
        for (SearchQuery searchQuery : SearchQuery.values()) {
            searchQueries.add(searchQuery);
        }
        return searchQueries;
    }
}
