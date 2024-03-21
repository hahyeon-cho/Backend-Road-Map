package ncnk.make.backendroadmap.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원가입 시 유저 권한 정보
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "guest"),
    USER("ROLE_USER", "user");

    private final String key;
    private final String title;
}