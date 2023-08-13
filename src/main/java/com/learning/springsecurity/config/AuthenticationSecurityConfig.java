package com.learning.springsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.learning.springsecurity.config.ApplicationUserRole.*;
import static com.learning.springsecurity.config.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;


    private final Logger logger = LoggerFactory.getLogger(AuthenticationSecurityConfig.class);

    public AuthenticationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.
                csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index/*","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        logger.info(String.format("Role {}, %s", STUDENT));
        logger.info(String.format("Role {}, %s", STUDENT.name()));
        logger.info(String.format("Role {}, %s", ADMIN));
        logger.info(String.format("Role {}, %s", ADMIN.name()));
       UserDetails user1 = User.builder()
               .username("manohar")
               .password(passwordEncoder.encode("password"))
               .authorities(STUDENT.getAuthorities())
               .build();
        UserDetails user2 = User.builder()
                .username("manohar1")
                .password(passwordEncoder.encode("password1"))
                .authorities(ADMIN.getAuthorities())
                .build();
        UserDetails user3 = User.builder()
                .username("manohar2")
                .password(passwordEncoder.encode("password2"))
                .authorities(ADMINTRAINEE.getAuthorities())
                .build();
       return new InMemoryUserDetailsManager(user1,user2,user3);
    }
}
