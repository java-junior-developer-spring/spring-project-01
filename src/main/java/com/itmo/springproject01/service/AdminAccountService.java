package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.User;
import com.itmo.springproject01.exception.ShopException;
import com.itmo.springproject01.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminAccountService {
    private final UserRepository userRepository;
    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminAccountService(UserRepository userRepository,
                               JwtSecurityService jwtSecurityService,
                               AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtSecurityService = jwtSecurityService;
        this.authenticationManager = authenticationManager;
    }

    public String authorize(String login, String password) throws ShopException {
        User user = userRepository.findByUsername(login)
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
