package com.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String employeeApi = "/api/employees/";
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, employeeApi).hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.GET, employeeApi + "**").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.POST, employeeApi).hasAuthority("MANAGER")
                .antMatchers(HttpMethod.PUT, employeeApi).hasAuthority("MANAGER")
                .antMatchers(HttpMethod.DELETE, employeeApi + "**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
