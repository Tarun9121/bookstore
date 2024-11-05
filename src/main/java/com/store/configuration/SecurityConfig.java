package com.store.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * I understand how it can be confusing, especially with the way Spring Security allows us to configure different types of authentication. Let's clarify the difference between `httpBasic()` and `authenticated()` methods in the context of Spring Security:
 *
 * ### 1. `httpBasic()`
 * - **Purpose**: This method enables HTTP Basic Authentication.
 * - **Functionality**:
 *   - When you call `httpBasic()`, you instruct Spring Security to authenticate users using Basic Authentication.
 *   - This method requires the client to send credentials (username and password) in the `Authorization` header with each request. The credentials are encoded in base64.
 *   - It's a simple authentication method often used for quick setups or internal APIs.
 *
 * **Example**:
 * ```java
 * http
 *     .csrf().disable()
 *     .authorizeRequests()
 *         .anyRequest().authenticated() // Any request needs authentication
 *     .and()
 *     .httpBasic(); // Enable HTTP Basic Authentication
 * ```
 *
 * ### 2. `authenticated()`
 * - **Purpose**: This method is used to specify that a request requires the user to be authenticated.
 * - **Functionality**:
 *   - When you use `authenticated()`, it means that any request matching the preceding conditions requires the user to have a valid session or token to access it.
 *   - This method does not dictate how the authentication should occur (like Basic Authentication or OAuth2). It simply checks if the user is authenticated before allowing access to that resource.
 *
 * **Example**:
 * ```java
 * http
 *     .authorizeRequests()
 *         .antMatchers("/public/**").permitAll() // Public endpoints
 *         .anyRequest().authenticated(); // Any other request must be authenticated
 * ```
 *
 * ### Putting Them Together
 * Here’s a more comprehensive example to show how they work together:
 *
 * ```java
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 *     http
 *         .csrf().disable() // Disables CSRF protection (not recommended for production without careful consideration)
 *         .authorizeRequests()
 *             .antMatchers("/public/**").permitAll() // No authentication needed for public endpoints
 *             .anyRequest().authenticated() // All other requests need to be authenticated
 *         .and()
 *         .httpBasic(); // Use Basic Authentication for the authentication mechanism
 *
 *     return http.build();
 * }
 * ```
 *
 * ### Key Differences
 * - **`httpBasic()`**:
 *   - Configures the type of authentication.
 *   - Enables HTTP Basic authentication, which requires credentials in the request header.
 *
 * - **`authenticated()`**:
 *   - Controls access to resources based on authentication.
 *   - Specifies that a request must come from an authenticated user, regardless of how that authentication is performed.
 *
 * ### Summary
 * - Use `httpBasic()` when you want to employ Basic Authentication in your application.
 * - Use `authenticated()` to specify that certain requests should only be accessible to authenticated users, without concern for how that authentication is achieved.
 *
 * If you have specific scenarios or further questions about using these methods, feel free to ask!
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

