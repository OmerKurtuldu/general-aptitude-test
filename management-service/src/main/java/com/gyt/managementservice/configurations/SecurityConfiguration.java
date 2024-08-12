package com.gyt.managementservice.configurations;

import com.gyt.corepackage.configuration.BaseSecurityService;
import com.gyt.corepackage.models.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final BaseSecurityService baseSecurityService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        baseSecurityService.configureCoreSecurity(http);
        http
                .authorizeHttpRequests(req -> req
                       .requestMatchers(HttpMethod.POST, "/api/v1/user/addOrganization").hasAnyAuthority(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/getById/{id}").hasAnyAuthority(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/getAll").hasAnyAuthority(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/v1/user/update").hasAnyAuthority(RoleType.ADMIN.name(),RoleType.ORGANIZATION.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/delete/{id}").hasAnyAuthority(RoleType.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/invitation/send").hasAnyAuthority(RoleType.ORGANIZATION.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

}
