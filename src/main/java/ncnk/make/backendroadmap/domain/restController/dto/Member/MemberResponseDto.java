package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Member;

/**
 * 회원 정보 Dto
 */

import java.util.List;

@Getter
@Setter
public class MemberResponseDto {
    private String profile; //프로필
    private String email; //이메일
    private String name; //이름
    private String nickName; //닉네임
    private String github; //깃허브
    private String profileImage;
    private int level; //대분류 레벨
    private int point; //알고리즘 포인트
    private int hard; // 상 문제
    private int normal; // 중 문제
    private int easy; // 하 문제
    private List<MemberRankingDto> memberRankingDtos;
    private List<MyRoadMapResponseDto> roadMapResponseDto; //마이페이지(MyRoadMap)
    private List<MyPracticeResponseDto> practiceResponseDto; //마이페이지(MyPractice)
    private List<MyTestResponseDto> testResponseDto; //마이페이지(MyTest)

    private MemberResponseDto(Member member) {
        this.profile = member.getProfile();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.github = member.getGithub();
        this.level = member.getLevel();
        this.point = member.getPoint();
        this.hard = member.getHard();
        this.normal = member.getNormal();
        this.easy = member.getEasy();
        this.profileImage = member.getProfile();
    }

    public static MemberResponseDto createMemberResponseDto(Member member) {
        return new MemberResponseDto(member);
    }
}
