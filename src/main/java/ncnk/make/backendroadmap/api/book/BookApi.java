package ncnk.make.backendroadmap.api.book;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.exception.FailToCallApiException;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.MainCategoryRepository;
import ncnk.make.backendroadmap.domain.repository.RecommendBookRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookApi {

    private final RecommendBookRepository recommendBookRepository;
    private final MainCategoryRepository mainCategoryRepository;

    public static final String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
    public static final String encode = "UTF-8";

    @Value("${book.api}")
    String apiKey;

    @Scheduled(cron = "0 0 0 1 * ?") // 매달 1일 0시 0분 0초
    @Counted("Counted.BookApi.callAladinApi")
    @Timed("BookApi.callAladinApi")
    @Transactional
    public void callAladinApi() {
        List<SearchQuery> searchQueries = SearchQuery.getSearchQueries();
        for (SearchQuery searchQuery : searchQueries) {
            try {
                // URL 및 쿼리 문자열 생성
                String encodedQuery = URLEncoder.encode(searchQuery.getQuery(), encode);
                String urlString = String.format(
                    "%s?QueryType=Title&Query=%s&MaxResults=3&output=js&version=20131101&ttbkey=%s&CategoryId=%d",
                    apiUrl, encodedQuery, apiKey, searchQuery.getField());
                log.info("urlString: {}", urlString);
                URL url = new URL(urlString);

                // HTTP 연결 설정
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // 응답 읽기
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();
                // 응답 데이터 처리
                String response = responseBuilder.toString();
                // JSON 파싱
                JSONObject jsonObject = new JSONObject(response);

                JSONArray items = jsonObject.getJSONArray("item");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject bookInfo = items.getJSONObject(i);
                    String title = bookInfo.getString("title");
                    String author = bookInfo.getString("author");
                    String cover = bookInfo.getString("cover");
                    String publisher = bookInfo.getString("publisher");

                    Main main = Main.getEnumByMainDocsOrder(searchQuery.getCategory());
                    MainCategory mainCategory = mainCategoryRepository.findMainCategoryByMainDocsTitle(main)
                        .orElseThrow(() -> new ResourceNotFoundException());

                    RecommendBook recommend = RecommendBook.createRecommend(title, author, cover,
                        publisher, mainCategory);
                    recommendBookRepository.save(recommend);
                }
                // 연결 종료
                connection.disconnect();
            } catch (Exception e) {
                log.error("[Error] Fail To Call Book API: " + e.getMessage());
                throw new FailToCallApiException();
            }
        }
    }
}