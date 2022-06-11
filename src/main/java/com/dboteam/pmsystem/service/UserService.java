package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.exception.NoSuchEntityException;
import com.dboteam.pmsystem.exception.SuchEntityAlreadyExistsException;
import com.dboteam.pmsystem.model.PositionName;
import com.dboteam.pmsystem.model.Project;
import com.dboteam.pmsystem.model.RoleName;
import com.dboteam.pmsystem.model.User;
import com.dboteam.pmsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CollaborationService collaborationService;
    private final PositionService positionService;

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

    public boolean checkUserPosition(String username, PositionName positionName, Project project) {
        User currentUser = findByUsername(username);
        return collaborationService.userHasPositionInProject(currentUser, positionService.getPositionByPositionName(positionName), project);
    }

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new SuchEntityAlreadyExistsException();
        }
        user.setPassword(new BCryptPasswordEncoder(8).encode(user.getPassword()));
        return userRepository.save(user);
    }
}