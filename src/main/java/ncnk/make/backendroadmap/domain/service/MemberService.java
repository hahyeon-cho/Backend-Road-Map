package ncnk.make.backendroadmap.domain.service;

import io.micrometer.core.annotation.Timed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.Member.MemberRepository;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 Service (BIZ 로직)
 */
@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TraceTemplate template;

    //회원 프로필 수정
    @Timed("MemberService.updateProfile")
    @Transactional
    public Long updateProfile(Member member, MemberUpdateRequestDto updateRequestDto) {
        Member updateMember = member.updateMember(updateRequestDto.getProfile(),
                updateRequestDto.getName(),
                updateRequestDto.getGithub());

        log.info("Member 프로필 수정 성공");

        return updateMember.getMemberId();
    }

    public List<Member> findTop5Point() {
        return memberRepository.Top5Point();
    }

    //회원 PK 이용해 회원 정보 조회
    public Member findMemberById(Long id) {
        return memberRepository.findMemberByMemberId(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    //이메일 이용해 회원 정보 조회
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
