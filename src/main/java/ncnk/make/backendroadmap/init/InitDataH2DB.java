package ncnk.make.backendroadmap.init;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.Book.BookApi;
import ncnk.make.backendroadmap.domain.constant.Constant;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.entity.Solved;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.repository.Quiz.QuizRepository;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDataH2DB {
    private final InitService initService;
    private static final Long initLikeCount = 1L;

    @PostConstruct
    public void init() {
        Member member = initService.initMember();
        List<MainCategory> mainCategories = initService.initCategory(member);
        initService.initQuiz(mainCategories);
        initService.insertBook();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    @Slf4j
    static class InitService {
        private final EntityManager em;
        @Value("${excel.file.path}")
        private String excelFilePath;
        private final QuizRepository quizRepository;
        private final InsertQuizService insertQuizService;

        private final BookApi bookApi;

        public void insertBook() {
            bookApi.callAladinApi();
        }

        public Member initMember() {
            Member member = Member.createMember("profile", "email", "name", "nickName", "github",
                    Constant.initLevel, Constant.initPoint, Role.GUEST, 0, 0, 0);
            em.persist(member);

            for (int i = 0; i < 30; i++) {
                if (i < 10) {
                    createInitHard(member);
                } else if (i >= 10 && i < 20) {
                    createInitMid(member);
                } else {
                    createInitEasy(member);
                }
            }
            return member;
        }

        private void createInitHard(Member member) {
            List<CodingTestAnswer> clist = new ArrayList<>();
            CodingTestAnswer codingTestAnswer = CodingTestAnswer.createCodingTestAnswer("input", "output");
            clist.add(codingTestAnswer);

            CodingTest codingTest = CodingTest.createCodingTest("HardName", "HardSlug", "Hard",
                    10.2, "Hard내용", null, clist, null);
            em.persist(codingTest);

            Solved solved = Solved.createSolved(codingTest, member, true, "제출 경로");
            em.persist(solved);
        }

        private void createInitMid(Member member) {
            List<CodingTestAnswer> codingTestAnswers = new ArrayList<>();
            CodingTestAnswer codingTestAnswer = CodingTestAnswer.createCodingTestAnswer("input", "output");
            codingTestAnswers.add(codingTestAnswer);

            CodingTest codingTest = CodingTest.createCodingTest("NormalName", "NormalSlug", "Normal",
                    50.7, "Mid내용", null, codingTestAnswers, null);
            em.persist(codingTest);

            Solved solved = Solved.createSolved(codingTest, member, false, "제출 경로");
            em.persist(solved);
        }

        private void createInitEasy(Member member) {
            List<CodingTestAnswer> clist = new ArrayList<>();
            CodingTestAnswer codingTestAnswer = CodingTestAnswer.createCodingTestAnswer("input", "output");
            clist.add(codingTestAnswer);

            CodingTest codingTest = CodingTest.createCodingTest("EasyName", "EasySlug", "Easy",
                    80.9, "Easy내용", null, clist, null);
            em.persist(codingTest);

            Solved solved = Solved.createSolved(codingTest, member, false, "제출 경로");
            em.persist(solved);
        }


        public List<MainCategory> initCategory(Member member) {
            List<Main> orderedMainDocs = Main.getOrderedMainDocs();
            List<MainCategory> mainCategories = new ArrayList<>();
            for (Main orderedMainDoc : orderedMainDocs) {
                MainCategory mainCategory = MainCategory.createMainCategory(orderedMainDoc, orderedMainDoc.getUrl());
                mainCategories.add(mainCategory);
                em.persist(mainCategory);

                List<Sub> orderedSubDocsInCategory = Sub.getOrderedSubDocsInCategory(mainCategory.getMainDocsOrder());

                for (Sub sub : orderedSubDocsInCategory) {
                    SubCategory subCategory = SubCategory.createSubCategory(sub, initLikeCount, sub.getSubDescription(),
                            mainCategory);
                    em.persist(subCategory);

                    DocsLike docsLike = DocsLike.createDocsLike(subCategory, member);
                    em.persist(docsLike);
                }
            }
            return mainCategories;
        }

        public void initQuiz(List<MainCategory> mainCategories) {
            log.info("excelPath = {}", excelFilePath);
            try {
                Quizs quizs = readQuizFromExcel(new File(excelFilePath), mainCategories);

                // 읽은 데이터 DB에 삽입
                for (Quiz quiz : quizs.getQuizs()) {
                    insertQuizService.insertQuiz(quiz);
                }

                List<Quiz> insertQuiz = quizRepository.findAll();
                log.info("=========================");
                log.info("insertedQuiz.size: {}", insertQuiz.size());
                log.info("=========================");
            } catch (IOException e) {
                log.error("Error reading Excel file: ", e.getMessage());
                e.printStackTrace();
            }
        }

        private Quizs readQuizFromExcel(File file, List<MainCategory> mainCategories) throws IOException {
            Quizs quizs = new Quizs();
            try (Workbook workbook = WorkbookFactory.create(file)) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.rowIterator();
                rowIterator.next(); // 컬럼명 스킵
                log.info("Read Excel Start");
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    String mainDoc = getStringCellValue(row.getCell(0));
                    String quizContext = getStringCellValue(row.getCell(1));
                    String quizAnswer = getStringCellValue(row.getCell(2));
                    String quizExplain = getStringCellValue(row.getCell(3));
                    MainCategory category = null;
                    for (MainCategory mainCategory : mainCategories) {
                        if (mainCategory.getMainDocsTitle().equals((Main.getInstance(mainDoc)))) {
                            category = mainCategory.getMainCategory(mainDoc);
                            break;
                        }
                    }
                    if (category != null) {
                        Quiz quiz = Quiz.createQuiz(quizContext, quizAnswer, quizExplain, category);
                        if (!quizs.getQuizs().contains(quiz)) {
                            quizs.getQuizs().add(quiz);
                        }
                    }
                }
            }
            return quizs;
        }

        private static String getStringCellValue(Cell cell) {
            if (cell == null) {
                return "";
            }
            return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
        }
    }
}