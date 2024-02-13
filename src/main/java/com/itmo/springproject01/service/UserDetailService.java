package com.itmo.springproject01.service;

import com.itmo.springproject01.entity.User;
import com.itmo.springproject01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // import com.itmo.springproject01.entity.User;
        User user = userRepository.findByUsername(username) // T get();
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

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
        // User
        // User implements UserDetails
        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(), Set.of(authority)
        );
    }
}
