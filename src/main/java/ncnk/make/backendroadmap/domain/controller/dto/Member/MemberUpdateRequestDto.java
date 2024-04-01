package ncnk.make.backendroadmap.domain.controller.dto.Member;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {
    private String nickName;
    private String github;

    private MemberUpdateRequestDto(String nickName, String github) {
        this.nickName = nickName;
        this.github = github;
    }

    public static MemberUpdateRequestDto createMemberUpdateRequest(String nickName, String github) {
        return new MemberUpdateRequestDto(nickName, github);
    }
}
