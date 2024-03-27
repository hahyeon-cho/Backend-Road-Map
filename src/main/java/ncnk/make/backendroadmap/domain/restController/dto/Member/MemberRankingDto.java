package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.Member;

@Getter
public class MemberRankingDto {
    private String profile;
    private String nickName;
    private int point;

    private MemberRankingDto(Member member) {
        this.profile = member.getProfile();
        this.nickName = member.getNickName();
        this.point = member.getPoint();
    }

    public static MemberRankingDto createMemberRankingDto(Member member) {
        return new MemberRankingDto(member);
    }
}
