package com.learning.springsecurity.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.learning.springsecurity.config.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
public class AuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;


    private final Logger logger = LoggerFactory.getLogger(AuthenticationSecurityConfig.class);

    public AuthenticationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/","/index/*","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        logger.info(String.format("Role {}", STUDENT));
        logger.info(String.format("Role {}", STUDENT.name()));
        logger.info(String.format("Role {}", ADMIN));
        logger.info(String.format("Role {}", ADMIN.name()));
       UserDetails user1 = User.builder()
               .username("manohar")
               .password(passwordEncoder.encode("password"))
               .roles(STUDENT.name())
               .build();
        UserDetails user2 = User.builder()
                .username("manohar1")
                .password(passwordEncoder.encode("password1"))
                .roles(ADMIN.name())
                .build();
       return new InMemoryUserDetailsManager(user1,user2);
    }
}
