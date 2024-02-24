package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.MemberRepository;
import ncnk.make.backendroadmap.domain.restController.dto.Member.MemberUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long updateProfile(Member member, MemberUpdateRequestDto updateRequestDto) {
        Member updateMember = member.updateMember(updateRequestDto.getProfile(),
                updateRequestDto.getName(),
                updateRequestDto.getGithub());

        log.info("Member 프로필 수정 성공");

        return updateMember.getMemberId();
    }

    public Member findMemberById(Long id) {
        return memberRepository.findMemberByMemberId(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
