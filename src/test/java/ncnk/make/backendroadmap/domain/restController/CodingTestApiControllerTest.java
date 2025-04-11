package ncnk.make.backendroadmap.domain.restController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
@Transactional
class CodingTestApiControllerTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CodingTestService codingTestService;

    @DisplayName("문제 리스트 페이지")
    @Test
    void getProblemListPage() throws Exception {
        mockMvc.perform(get("/api/codingtest")
                .param("status", "unsolved"))
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageSize").value(5))
            .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("알고리즘 문제 풀기 페이지")
    @Test
    void problemPage() throws Exception {
        // given
        CodingTest codingTest = createEasyLevel();

        // when & then
        mockMvc.perform(get("/api/codingtest/{id}", codingTest.getCodingTestId()))
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.problemTitle").value(codingTest.getProblemTitle()))
            .andExpect(jsonPath("$.data.problemLevel").value(codingTest.getProblemLevel()));
    }

    private CodingTest createEasyLevel() {
        String json = "[{\"input\":\"input\", \"output\":\"output\"}]";
        JSONArray jsonArray = new JSONArray(json);

        List<CodingTestAnswer> codingTestAnswers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String input = jsonObject.getString("input");
            String output = jsonObject.getString("output");
            CodingTestAnswer codingTestAnswer = CodingTestAnswer.createCodingTestAnswer(input, output);
            codingTestAnswers.add(codingTestAnswer);
        }

        CodingTest codingTest = CodingTest.createCodingTest("EasyName", "EasySlug", "Easy",
            50.7, "EasyContents", null, codingTestAnswers, null);
        em.persist(codingTest);
        return codingTest;
    }


}