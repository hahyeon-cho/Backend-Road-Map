package ncnk.make.backendroadmap.domain.restController.dto.Member;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Member;

@Getter
@Setter
public class MemberResponseDto {
    private String profile;
    private String email;
    private String name;
    private String nickName;
    private String github;
    private int level;
    private int point;
    private List<MyRoadMapResponseDto> roadMapResponseDto;
    private List<MyPracticeResponseDto> practiceResponseDto;
    private List<MyTestResponseDto> testResponseDto;

    private MemberResponseDto(Member member) {
        this.profile = member.getProfile();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.github = member.getGithub();
        this.level = member.getLevel();
        this.point = member.getPoint();
    }

    public static MemberResponseDto createMemberResponseDto(Member member) {
        return new MemberResponseDto(member);
    }
}
