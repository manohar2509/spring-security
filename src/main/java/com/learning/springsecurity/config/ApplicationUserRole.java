package com.learning.springsecurity.config;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learning.springsecurity.config.ApplicationUserPermission.*;
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),

    ADMINTRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions){
        this.permissions = permissions;
    }
    private Set<ApplicationUserPermission> getPermission() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        System.out.println(this.permissions);
        Set<SimpleGrantedAuthority> authorities = getPermission().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        System.out.println(authorities);
        return authorities;

    }


}
