package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.exception.NoSuchEntityException;
import com.dboteam.pmsystem.model.RoleName;
import com.dboteam.pmsystem.model.User;
import com.dboteam.pmsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoleName()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(NoSuchEntityException::new);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(RoleName roleName) {
        return Collections.singleton(new SimpleGrantedAuthority(roleName.name()));
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}