package ncnk.make.backendroadmap.api.leetcode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LeetCodeApi {
    private static final String LEETCODE_API_URL = "https://leetcode.com/api/problems/algorithms/";
    private static final String PAID_ONLY = "paid_only";
    private static final String STAT_STATUS_PAIRS = "stat_status_pairs";

    public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
        String json = Jsoup.connect(LEETCODE_API_URL).ignoreContentType(true).execute().body();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray problemsArray = jsonObject.getJSONArray(STAT_STATUS_PAIRS);

        List<JSONObject> freeProblems = new ArrayList<>();
        for (int i = 0; i < problemsArray.length(); i++) {
            JSONObject problem = problemsArray.getJSONObject(i);
            if (!problem.getBoolean(PAID_ONLY)) {
                freeProblems.add(problem);
            }
        }

        return freeProblems;
    }
}