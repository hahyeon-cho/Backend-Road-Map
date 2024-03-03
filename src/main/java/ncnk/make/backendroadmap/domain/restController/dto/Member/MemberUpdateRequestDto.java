package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;

/**
 * 회원 업데이트 request
 */

@Getter
public class MemberUpdateRequestDto {
    private String profile; //프로필
    private String name; //이름
    private String github; //깃허브 주소

    private MemberUpdateRequestDto(String profile, String name, String github) {
        this.profile = profile;
        this.name = name;
        this.github = github;
    }

    public static MemberUpdateRequestDto createMemberUpdateRequest(String profile, String name, String github) {
        return new MemberUpdateRequestDto(profile, name, github);
    }
}
