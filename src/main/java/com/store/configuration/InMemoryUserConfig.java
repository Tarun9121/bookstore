package com.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                .username("john")
                .password("test123")
                .authorities("EMPLOYEE")
                .build();
        UserDetails mary = User.builder()
                .username("mary")
                .password("test123")
                .authorities("EMPLOYEE", "MANAGER")
                .build();
        UserDetails susan = User.builder()
                .username("susan")
                .password("test123")
                .authorities("EMPLOYEE", "MANAGER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, susan);
    }
}