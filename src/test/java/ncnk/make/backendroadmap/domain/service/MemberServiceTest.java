package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Role;
import ncnk.make.backendroadmap.domain.repository.MemberRepository;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberUpdateRequestDto;
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
        MemberUpdateRequestDto memberUpdateRequest = MemberUpdateRequestDto.createMemberUpdateRequest(profile, name,
                gitHub);

        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // when
        Long updateProfileId = memberService.updateProfile(member, memberUpdateRequest);
        Optional<Member> findUpdateMember = memberRepository.findById(updateProfileId);

        // then
        assertAll(
                () -> assertThat(findUpdateMember).isPresent(),
                () -> assertThat(findUpdateMember.get().getProfile()).isEqualTo(profile),
                () -> assertThat(findUpdateMember.get().getName()).isEqualTo(name),
                () -> assertThat(findUpdateMember.get().getGithub()).isEqualTo(gitHub)
        );
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
        Member member = Member.createMember("profile", "email1", "name", "nickname", "github", 1, 0, Role.GUEST);
        em.persist(member);
        return member;
    }
}