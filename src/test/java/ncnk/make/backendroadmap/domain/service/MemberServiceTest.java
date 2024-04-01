package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.repository.Member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @PersistenceContext
    EntityManager em;

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @DisplayName("유저 프로필 업데이트 테스트")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"updateProfile, updateName, updateGitHub"})
    void updateProfileTest(String profile, String name, String gitHub) {
        // given
        Member member = createMember();
        MemberUpdateRequestDto memberUpdateRequest = MemberUpdateRequestDto.createMemberUpdateRequest(name,
                gitHub);

        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // when
        Long updateProfileId = memberService.updateProfile(member, null, memberUpdateRequest);
        Optional<Member> findUpdateMember = memberRepository.findById(updateProfileId);

        // then
        assertAll(
                () -> assertThat(findUpdateMember).isPresent(),
                () -> assertThat(findUpdateMember.get().getProfile()).isEqualTo(profile),
                () -> assertThat(findUpdateMember.get().getName()).isEqualTo(name),
                () -> assertThat(findUpdateMember.get().getGithub()).isEqualTo(gitHub)
        );
    }

    @DisplayName("멤버 포인트: 탑 5 찾기")
    @Test
    void findTop5PointTest() {
        // given
        List<Member> expectedTop5Members = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Member member = Member.createMember("profile" + i, "email" + i, "name" + i, "nickname" + i, "github" + i,
                    1, 10 - i, Role.GUEST, 0, 0, 0);
            expectedTop5Members.add(member);
        }

        when(memberRepository.top5Point()).thenReturn(expectedTop5Members);

        // when
        List<Member> top5Point = memberService.findTop5Point();

        // then
        assertThat(top5Point).isEqualTo(expectedTop5Members);
    }

    @DisplayName("유저 PK 값으로 유저 찾기")
    @Test
    void findMemberByIdTest() {
        //given
        Member member = createMember();
        when(memberRepository.findMemberByMemberId(member.getMemberId())).thenReturn(Optional.of(member)); // Mock 설정 추가

        //when
        Member findMember = memberService.findMemberById(member.getMemberId());

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @DisplayName("유저 Email 값으로 유저 찾기")
    @Test
    void findMemberByEmailTest() {
        //given
        Member member = createMember();
        when(memberRepository.findMemberByEmail(member.getEmail())).thenReturn(Optional.of(member)); // Mock 설정 추가

        //when
        Member findMember = memberService.findMemberByEmail(member.getEmail());

        //then
        assertThat(findMember).isEqualTo(member);
    }


    private Member createMember() {
        Member member = Member.createMember("profile", "email1", "name", "nickname", "github",
                1, 0, Role.GUEST, 0, 0, 0);
        em.persist(member);
        return member;
    }
}