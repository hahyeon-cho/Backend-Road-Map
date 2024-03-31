package ncnk.make.backendroadmap.api.leetcode;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LeetCodeApiTest.class)
@ExtendWith(MockitoExtension.class)
public class LeetCodeApiTest {
    private LeetCodeApi leetCodeApi = new MockLeetCodeApi();

    class MockLeetCodeApi extends LeetCodeApi {
        @Override
        public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
            List<JSONObject> mockData = new ArrayList<>();
            mockData.add(new JSONObject("{\"stat\":{\"question_id\":1, \"question__title\":\"Mock Problem 1\"}, \"paid_only\":false}"));
            mockData.add(new JSONObject("{\"stat\":{\"question_id\":2, \"question__title\":\"Mock Problem 2\"}, \"paid_only\":false}"));
            return mockData;
        }
    }

    @Test
    public void getLeetCodeProblemListTest() throws IOException {
        List<JSONObject> result = leetCodeApi.getLeetCodeProblemList();

        assertNotNull(result, "The problem list should not be null");
        assertEquals(2, result.size());

        JSONObject firstProblem = result.get(0);
        assertEquals(1, firstProblem.getJSONObject("stat").getInt("question_id"));
        assertEquals("Mock Problem 1", firstProblem.getJSONObject("stat").getString("question__title"));

        JSONObject secondProblem = result.get(1);
        assertEquals(2, secondProblem.getJSONObject("stat").getInt("question_id"));
        assertEquals("Mock Problem 2", secondProblem.getJSONObject("stat").getString("question__title"));
    }
}

