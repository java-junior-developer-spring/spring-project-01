package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.CustomUser;
import com.itmo.springproject01.entity.Role;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.CustomUserRepository;
import com.itmo.springproject01.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final CustomUserRepository customUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(CustomUserRepository customUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(CustomUser customUser) throws ShopException {
        customUser.setRole(roleRepository.findByRoleType(Role.RoleType.ROLE_USER)
                .orElseThrow(()->new ShopException("Роль не найдена")));
        customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
        customUserRepository.save(customUser);
    }
}
