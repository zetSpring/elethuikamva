package elethu.ikamva.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static elethu.ikamva.domain.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_READ,
            ADMIN_WRITE,
            MEMBER_READ,
            MEMBER_WRITE,
            MEMBER_DELETE,
            MEMBER_UPDATE)),
    MEMBER(Set.of(MEMBER_READ, MEMBER_WRITE, MEMBER_DELETE, MEMBER_UPDATE));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
