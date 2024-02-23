package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.CustomUser;
import com.itmo.springproject01.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    private final CustomUserRepository customUserRepository;

    @Autowired
    public UserDetailService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // import com.itmo.springproject01.entity.CustomUser;
        CustomUser user = customUserRepository.findByUsername(username) // T get();
                .orElseThrow(()->new UsernameNotFoundException("CustomUser not found"));

        /*
        List<GrantedAuthority> roles = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(
                        role.getRoleType().name()
                ));
        */

        GrantedAuthority authority = new SimpleGrantedAuthority(
                user.getRole().getRoleType().name()
        );
        // UserDetails
        // CustomUser
        // CustomUser implements UserDetails
        return new User(username, user.getPassword(), Set.of(authority));
    }
}
