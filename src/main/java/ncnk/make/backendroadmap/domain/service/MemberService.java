package ncnk.make.backendroadmap.domain.service;

import io.micrometer.core.annotation.Timed;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.controller.dto.member.MemberUpdateRequestDto;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.member.MemberRepository;
import ncnk.make.backendroadmap.domain.utils.AttachImage;
import ncnk.make.backendroadmap.domain.utils.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TraceTemplate template;

    // 회원 프로필 수정
    private final UploadService uploadService;

    @Value("${img.path}")
    private String userImage;

    /**
     * Controller -> PostMapping(/edit) 구글 로그인 직후 사용자 정보가 변경되는 페이지에서 확인 버튼을 누르는 순간 작동되는 메서드
     **/
    @Timed("MemberService.updateProfile")
    @Transactional
    public Long updateProfile(Member member, MultipartFile uploadFile, MemberUpdateRequestDto updateRequestDto) {
        log.info("upload: {}", uploadFile.getOriginalFilename());
        AttachImage upload = uploadService.upload(userImage, uploadFile, member.getNickName());

        if (upload == null) {
            // 업로드 실패 시 적절한 처리를 수행하고 예외를 던지거나 로그를 출력합니다.
            log.error("Failed to upload image for member {}", member.getNickName());
            // 예외를 던지거나 기본 이미지로 대체하는 등의 처리를 수행할 수 있습니다.
            throw new RuntimeException("Failed to upload image for member " + member.getNickName());
        }

        Member updateMember = member.updateMember(upload.getUploadPath(),
            updateRequestDto.getNickName(),
            updateRequestDto.getGithub());

        log.info("Member 프로필 수정 성공");

        return updateMember.getMemberId();
    }

    public void updateLevel(Member member) {
        member.updateLevel();
    }

    public List<Member> findTop5Point() {
        return memberRepository.top5Point();
    }


    public Member findMemberById(Long id) {
        Member member = memberRepository.findMemberByMemberId(id)
            .orElseThrow(() -> new ResourceNotFoundException());
        return member;
    }

    // 이메일 이용해 회원 정보 조회
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException());
    }
}
