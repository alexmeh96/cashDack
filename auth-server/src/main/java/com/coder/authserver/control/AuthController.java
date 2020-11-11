package com.coder.authserver.control;

import com.coder.authserver.dto.Token;
import com.coder.authserver.dto.UserDto;
import com.coder.authserver.model.ERole;
import com.coder.authserver.model.Role;
import com.coder.authserver.model.User;
import com.coder.authserver.payload.*;
import com.coder.authserver.repo.RoleRepository;
import com.coder.authserver.repo.UserRepository;
import com.coder.authserver.security.JwtUtils;
import com.coder.authserver.service.UserDetailsImpl;
import com.coder.authserver.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private CookieUtil cookieUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.cookieUtil = cookieUtil;
    }

    @CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUser loginUser, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Token accessToken = jwtUtils.generateAccessToken(userDetails.getEmail());
        Token refreshToken = jwtUtils.generateRefreshToken(userDetails.getEmail());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createTokenCookie(refreshToken).toString());
   //     response.addCookie(cookieUtil.createTokenCookie2(refreshToken));

        User user = userRepository.findByEmail(userDetails.getEmail()).orElseThrow();
        user.setTokenRefresh(refreshToken.getTokenValue());
        userRepository.save(user);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok().headers(responseHeaders).body(new LoginResponse(
                        accessToken,
                        new UserDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                roles)
                )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUser registerUser) {
        if (userRepository.existsByUsername(registerUser.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerUser.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(registerUser.getUsername(),
                registerUser.getEmail(),
                passwordEncoder.encode(registerUser.getPassword()));

        Set<String> strRoles = registerUser.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new RegisterResponse("User registered successfully!"));
    }
    @CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        String currentUserEmail = jwtUtils.getUserNameFromRefreshToken(refreshToken);

        Token newAccessToken = jwtUtils.generateAccessToken(currentUserEmail);
        Token newRefreshToken = jwtUtils.generateRefreshToken(currentUserEmail);

        User user = userRepository.findByEmail(currentUserEmail).get();
        user.setTokenRefresh(newRefreshToken.getTokenValue());
        userRepository.save(user);

      //  response.addCookie(cookieUtil.createTokenCookie2(newRefreshToken));


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createTokenCookie(newRefreshToken).toString());

        return ResponseEntity.ok().headers(responseHeaders).body(new LoginResponse(
                newAccessToken,
                        new UserDto(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList())
                        )
                )

        );
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> logout(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        user.setTokenRefresh("");
        userRepository.save(user);
        return ResponseEntity.ok().body("logout is success!");
    }

}