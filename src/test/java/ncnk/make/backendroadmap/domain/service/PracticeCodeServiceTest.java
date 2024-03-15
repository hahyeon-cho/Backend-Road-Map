package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.repository.PracticeCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PracticeCodeServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    PracticeCodeService practiceCodeService;

    @MockBean
    private PracticeCodeRepository practiceCodeRepository;


    @DisplayName("웹 컴파일러 저장")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"testFile, /test/path, .java"})
    void saveTest(String fileName, String filePath, String extension) {
        //given
        Member member = createMember();
        PracticeCode practiceCode = PracticeCode.createPracticeCode(fileName, filePath, extension, member);

        when(practiceCodeRepository.findAll()).thenReturn(Collections.singletonList(practiceCode));

        //when
        practiceCodeService.save(fileName, filePath, extension, member);

        //then
        assertAll(() -> assertEquals(1, practiceCodeRepository.findAll().size()),
                () -> verify(practiceCodeRepository).save(any(PracticeCode.class)));
    }

    @DisplayName("마이페이지: MyTest")
    @Test
    void getPracticesByMemberTest() {
        // given
        Member member = createMember();
        Pageable pageable = PageRequest.of(0, 6); // 페이지 번호 0(첫 번째 페이지), 페이지 당 항목 수 10
        List<PracticeCode> practiceCodeList = new ArrayList<>(); // 테스트 데이터 생성
        Page<PracticeCode> practiceCodePage = new PageImpl<>(practiceCodeList, pageable, practiceCodeList.size());

        PracticeCodeService practiceCodeService = mock(PracticeCodeService.class);
        when(practiceCodeService.getPracticesByMember(any(Member.class), any(Pageable.class))).thenReturn(
                practiceCodePage);

        // when
        Page<PracticeCode> result = practiceCodeService.getPracticesByMember(member, pageable);

        // then
        assertAll(() -> assertNotNull(result),
                () -> assertThat(practiceCodeList.size()).isEqualTo(result.getContent().size()),
                () -> verify(practiceCodeService).getPracticesByMember(member, pageable)); // 서비스 메서드가 예상대로 호출되었는지 검증
    }


    private Member createMember() {
        Member member = Member.createMember("profile", "email", "name", "nickname", "github", 1, 0, Role.GUEST);
        em.persist(member);
        return member;
    }
}