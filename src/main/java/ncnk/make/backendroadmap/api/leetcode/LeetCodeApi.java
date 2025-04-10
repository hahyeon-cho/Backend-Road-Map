package ncnk.make.backendroadmap.api.leetcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LeetCodeApi {

    private static final String LEETCODE_API_URL = "https://leetcode.com/api/problems/algorithms/";
    private static final String PAID_ONLY = "paid_only";
    private static final String STAT_STATUS_PAIRS = "stat_status_pairs";

    public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(LEETCODE_API_URL)
            .addHeader("User-Agent", "Mozilla/5.0")
            .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONObject jsonObject = new JSONObject(responseBody);
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