package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.Member;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private String profile;
    private String email;
    private String name;
    private String github;
    private int level;
    private List<MyRoadMapResponseDto> roadMapResponseDto;
    private List<MyPracticeResponseDto> practiceResponseDto;
    private List<MyTestResponseDto> testResponseDto;

    private MemberResponseDto(Member member) {
        this.profile = member.getProfile();
        this.email = member.getEmail();
        this.name = member.getName();
        this.github = member.getGithub();
        this.level = member.getLevel();
    }

    public static MemberResponseDto createMemberResponseDto(Member member) {
        return new MemberResponseDto(member);
    }
}
