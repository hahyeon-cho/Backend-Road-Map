package com.make.backendroadmap.domain.security.auth.dto;

import com.make.backendroadmap.domain.entity.Member;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
    private final String name;
    private final String email;
    private final String picture;
    
    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getProfile();
    }
}