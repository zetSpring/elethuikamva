package elethu.ikamva.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static elethu.ikamva.domain.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(ADMIN_UPDATE, ADMIN_DELETE, ADMIN_READ, ADMIN_WRITE, USER_READ, USER_WRITE, USER_DELETE, USER_UPDATE)),
    MEMBER(Set.of(USER_READ, USER_WRITE, USER_DELETE, USER_UPDATE));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
