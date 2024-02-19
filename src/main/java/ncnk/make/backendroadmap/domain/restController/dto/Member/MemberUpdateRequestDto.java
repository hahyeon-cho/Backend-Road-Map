package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {
    private String profile;
    private String name;
    private String github;

    private MemberUpdateRequestDto(String profile, String name, String github) {
        this.profile = profile;
        this.name = name;
        this.github = github;
    }

    public static MemberUpdateRequestDto createMemberUpdateRequest(String profile, String name, String github) {
        return new MemberUpdateRequestDto(profile, name, github);
    }
}
