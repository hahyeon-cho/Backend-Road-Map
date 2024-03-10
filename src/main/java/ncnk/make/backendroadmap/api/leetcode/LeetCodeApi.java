package ncnk.make.backendroadmap.api.leetcode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LeetCodeApi {
    private static final String LEETCODE_API_URL = "https://leetcode.com/api/problems/algorithms/";
    private static final String PAID_ONLY = "paid_only";
    private static final String STAT_STATUS_PAIRS = "stat_status_pairs";

    public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
        log.info("is start?????");
        String json = Jsoup.connect(LEETCODE_API_URL)
                .userAgent("Chrome")
                .method(Method.valueOf("GET"))
                .header("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en-US,en;q=0.9,ko;q=0.8")
                .header("Cache-Control", "max-age=0")
                .header("Cookie",
                        "csrftoken=ThkkTBrloKf5TPuiVFB6nfCVPdnjdSo2HumgIPdDISSzVdh3OENcFkhAWCVME4Jm; __cf_bm=8Tywpel.ZLhI.OGpXrva_KERawER5KIi1zIYJ.uLeFc-1709809647-1.0.1.1-7gnJlYYhMzEk0tL215yk7DdspRE7I_W26eguKmsEqIwxMNISD51cKrNYfUDpQcwxLGIwLmjOzp08n9SxCvzqyQ")
                .header("Dnt", "1")
                .header("Upgrade-Insecure-Requests", "1")
//                .userAgent(
//                        "Mozilla/5.0 (Macintosh; Intel생 Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
//                .userAgent("Mozilla/5.0")
                .execute().body();
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