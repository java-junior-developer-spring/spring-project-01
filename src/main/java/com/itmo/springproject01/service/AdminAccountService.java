package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.CustomUser;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.CustomUserRepository;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountService {
    private final CustomUserRepository customUserRepository;
    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminAccountService(CustomUserRepository customUserRepository,
                               JwtSecurityService jwtSecurityService,
                               AuthenticationManager authenticationManager) {
        this.customUserRepository = customUserRepository;
        this.jwtSecurityService = jwtSecurityService;
        this.authenticationManager = authenticationManager;
    }

    public String authorize(String login, String password) throws ShopException {
        CustomUser user = customUserRepository.findByUsername(login)
                .orElseThrow(()->new ShopException("Пользователь не найден"));

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token;
        try {
            token = jwtSecurityService.generateToken(user);
        } catch (JOSEException e) {
            throw new ShopException(e.getMessage());
        }
        return token;
    }
}
