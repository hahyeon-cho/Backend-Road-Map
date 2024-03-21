package ncnk.make.backendroadmap.domain.controller.dto.Member;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String profile;
    private String nickName;
    private String github;

    private MemberUpdateRequestDto(String profile, String nickName, String github) {
        this.profile = profile;
        this.nickName = nickName;
        this.github = github;
    }

    public static MemberUpdateRequestDto createMemberUpdateRequest(String profile, String nickName, String github) {
        return new MemberUpdateRequestDto(profile, nickName, github);
    }
}
