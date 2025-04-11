package ncnk.make.backendroadmap.domain.security.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Member;

@Getter
@Setter
public class SessionUser implements Serializable {

    private final String name;
    private final String email;
    private String picture;
    private String github;
    private String nickName;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getProfile();
        this.github = member.getGithub();
        this.nickName = member.getNickName();
    }
}