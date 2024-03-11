package ncnk.make.backendroadmap.domain.security.auth.dto;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.constant.Constant;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Role;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    private OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
                            String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes createOAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        return new OAuthAttributes(attributes, nameAttributeKey, name, email, picture);
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }


    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String name = (String) response.get("name");
        String email = (String) response.get("email");
        String picture = (String) response.get("picture");

        return createOAuthAttributes(response, userNameAttributeName, name, email, picture);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");

        return createOAuthAttributes(attributes, userNameAttributeName, name, email, picture);
    }

    public Member toEntity() {
        return Member.createMember(picture, email, name, null, "git", Constant.initLevel, Role.GUEST);
    }
}