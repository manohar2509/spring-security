package com.learning.springsecurity.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;

public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("admin:read"),
    COURSE_WRITE("admin:write");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return this.permission;
    }




}
