package com.hyusein.mustafa.todoapp.service;

import com.hyusein.mustafa.todoapp.model.Privilege;
import com.hyusein.mustafa.todoapp.model.Role;
import com.hyusein.mustafa.todoapp.model.User;
import com.hyusein.mustafa.todoapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException("User not found.");

        log.info("loadUserByUsername() {}", username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> list = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        list.addAll(roles.stream()
                .map(Role::getPrivileges)
                .iterator()
                .next()
                .stream()
                .map(Privilege::getName)
                .collect(Collectors.toList())
        );
        return list;
    }
}
