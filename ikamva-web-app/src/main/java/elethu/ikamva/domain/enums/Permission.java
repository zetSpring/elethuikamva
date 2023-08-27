package elethu.ikamva.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_WRITE("admin:write"),
    MEMBER_READ("member:read"),
    MEMBER_WRITE("member:write"),
    MEMBER_DELETE("member:delete"),
    MEMBER_UPDATE("member:update");

    private final String permission;
}
