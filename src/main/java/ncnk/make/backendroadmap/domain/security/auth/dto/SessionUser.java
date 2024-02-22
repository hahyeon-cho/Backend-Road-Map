package ncnk.make.backendroadmap.domain.security.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.Member;

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