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
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.repository.QuizRepository;
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
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void init() {
        Member member = initService.initMember();
        List<MainCategory> mainCategories = initService.initCategory(member);
        initService.initQuiz(mainCategories);
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
            Member member = Member.createMember("profile", "email", "name", "nickName", "github", 1, Role.GUEST);
            em.persist(member);

            return member;
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
                    SubCategory subCategory = SubCategory.createSubCategory(sub, 0L,
                            mainCategory); //TODO : constant likeCount
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
                    //TODO: 리트코드 API가 추가된 후 Insert하는 비즈니스 로직을 작성한다. 그리고 엑셀에서 대분류 5(Algorithm) CS 문제는 삭제한다.
//                    if (quizAnswer.contains("|")) {
//                        String[] parts = quizAnswer.split("\\|");
//                        String problemInput = parts[0].trim();
//                        String problemOutput = parts[1].trim();
//                        for (MainCategory mainCategory : mainCategories) {
//                            if (mainCategory.getMainDocsTitle().equals((Main.getInstance(mainDoc)))) {
//                                category = mainCategory.getMainCategory(mainDoc);
//                                break;
//                            }
//                        }
//                        if (category != null) {
//                            CodingTest.createAlgorithmTest(quizContext, problemInput, problemOutput,
//                                    quizExplain, category);
//                            if (!quizs.getQuizs().contains(quiz)) {
//                                quizs.getQuizs().add(quiz);
//                            }
//                        }
//                    }
//                    if (!quizAnswer.contains("|")) {
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
//                    }
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