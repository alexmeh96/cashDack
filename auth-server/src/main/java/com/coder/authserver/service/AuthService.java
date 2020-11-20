package com.coder.authserver.service;

import com.coder.authserver.dao.ERole;
import com.coder.authserver.dao.Role;
import com.coder.authserver.dto.LoginResponseDTO;
import com.coder.authserver.dao.User;
import com.coder.authserver.repo.RoleRepository;
import com.coder.authserver.repo.UserRepository;
import com.coder.authserver.util.CookieUtil;
import com.coder.authserver.util.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public User findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Boolean userExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public Role findRole(ERole name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }



}
