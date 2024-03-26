package ncnk.make.backendroadmap.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.controller.dto.Member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.DuplicateResourceException;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.Member.MemberRepository;
import ncnk.make.backendroadmap.domain.utils.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UploadService uploadService;

    @Value("${img.path}")
    private String userImage;

    /**
     * Controller -> PostMapping(/edit)
     * 구글 로그인 직후 사용자 정보가 변경되는 페이지에서 확인 버튼을 누르는 순간 작동되는 메서드
     **/

    @Transactional
    public Long updateProfile(Member member, MemberUpdateRequestDto updateRequestDto) {
        Member updateMember = member.updateMember(updateRequestDto.getProfile(),
                updateRequestDto.getNickName(),
                updateRequestDto.getGithub());
                updateRequestDto.getProfile();

        log.info("Member 프로필 수정 성공");

        return updateMember.getMemberId();
    }

    public List<Member> findTop5Point() {
        return memberRepository.top5Point();
    }


    public Member findMemberById(Long id) {
        Member member = memberRepository.findMemberByMemberId(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return member;
    }

    //이메일 이용해 회원 정보 조회
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
