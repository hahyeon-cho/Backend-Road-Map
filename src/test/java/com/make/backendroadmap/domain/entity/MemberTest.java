package com.make.backendroadmap.domain.entity;

import com.make.backendroadmap.domain.repository.MemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("test")
    @Test
    void basicTest() {
        //given
        MainCategory mainCategory = new MainCategory();
        Member member = Member.createMember("profile", "email", "name", "github", 1);

        //when
        Member saveMember = memberRepository.save(member);

        //then
        List<Member> members = memberRepository.findAll();
        Assertions.assertThat(members.size()).isEqualTo(1);
        Assertions.assertThat(members.get(0).getEmail()).isEqualTo("email");
    }
}