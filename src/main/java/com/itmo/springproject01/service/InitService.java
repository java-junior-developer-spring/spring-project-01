package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.CustomUser;
import com.itmo.springproject01.entity.Role;
import com.itmo.springproject01.repository.CustomUserRepository;
import com.itmo.springproject01.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private RoleRepository roleRepository;
    private CustomUserRepository customUserRepository;
    private PasswordEncoder passwordEncoder;

    public InitService(RoleRepository roleRepository, CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void createUserRole() {
        if (!roleRepository.existsByRoleType(Role.RoleType.ROLE_USER)) {
            Role user = new Role();
            user.setRoleType(Role.RoleType.ROLE_USER);
            roleRepository.save(user);
        }
    }

    @Bean
    public void createAdminAndAdminRole() {
        if (!customUserRepository.existsByUsername("admin")) {
            CustomUser admin = new CustomUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(roleRepository.findByRoleType(Role.RoleType.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleType(Role.RoleType.ROLE_ADMIN);
                        return roleRepository.save(role);
                    }));
            admin.setEmail("admin@gmail.com");
            customUserRepository.save(admin);
        }
    }
}
