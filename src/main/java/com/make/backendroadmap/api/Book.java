package com.make.backendroadmap.api;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class Book {

    @Value("${book.api}")
    static String apiKey;

    public static void main(String[] args) {
        try {
            log.info("apiKey ={}", apiKey);

            String query = "git";  //검색어
            String apiUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
            int categoryId = 351; // 분야 '컴퓨터/모바일'


            // URL 및 쿼리 문자열 생성
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String urlString = String.format("%s?QueryType=Title&Query=%s&MaxResults=3&output=js&version=20131101&ttbkey=%s&CategoryId=%d", apiUrl, encodedQuery, apiKey, categoryId);

            // URL 객체 생성
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
                //String category = bookInfo.getString("categoryName");

                System.out.println("표지: " + cover);
                System.out.println("제목: " + title);
                System.out.println("저자: " + author);
                //System.out.println("분야: " + category);
                System.out.println();
            }


            System.out.println(response);

            // 연결 종료
            connection.disconnect();
        } catch (Exception e) {
            System.err.println("API 요청 중 오류 발생: " + e.getMessage());
        }
    }
}
