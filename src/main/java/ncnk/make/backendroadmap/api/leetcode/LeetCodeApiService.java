package ncnk.make.backendroadmap.api.leetcode;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LeetCodeApiService {
    private final String LEETCODE_API_URL = "https://leetcode.com/api/problems/algorithms/";

    public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
        String json = Jsoup.connect(LEETCODE_API_URL).ignoreContentType(true).execute().body();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray problemsArray = jsonObject.getJSONArray("stat_status_pairs");

        List<JSONObject> freeProblems = new ArrayList<>();
        for (int i = 0; i < problemsArray.length(); i++) {
            JSONObject problem = problemsArray.getJSONObject(i);
            if (!problem.getBoolean("paid_only")) {
                freeProblems.add(problem);
            }
        }

        return freeProblems;
    }
}