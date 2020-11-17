package com.coder.authserver.control;

import com.coder.authserver.dto.LoginRequestDTO;
import com.coder.authserver.dto.LoginResponseDTO;
import com.coder.authserver.dto.RegisterRequestDTO;
import com.coder.authserver.dto.RegisterResponseDTO;
import com.coder.authserver.mappers.AuthMapper;
import com.coder.authserver.model.Token;
import com.coder.authserver.dao.ERole;
import com.coder.authserver.dao.Role;
import com.coder.authserver.dao.User;
import com.coder.authserver.service.AuthService;
import com.coder.authserver.util.security.JwtUtils;
import com.coder.authserver.service.UserDetailsImpl;
import com.coder.authserver.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private CookieUtil cookieUtil;
    @Autowired
    private AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.cookieUtil = cookieUtil;
    }

    @CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Token refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
        Token accessToken = jwtUtils.generateAccessToken(userDetails.getUsername());

        User user = authService.findUser(userDetails.getUsername());
        user.setTokenRefresh(refreshToken.getTokenValue());
        user = authService.saveUser(user);

//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());

        HttpHeaders responseHeaders = cookieUtil.createTokenCookieHeader(refreshToken);

        LoginResponseDTO loginResponseDTO = AuthMapper.toLoginResponseDTO(user, accessToken);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponseDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        if (authService.userExistUsername(registerRequestDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponseDTO("Error: Username is already taken!"));
        }

        if (authService.userExistEmail(registerRequestDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterResponseDTO("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(registerRequestDTO.getUsername(),
                registerRequestDTO.getEmail(),
                passwordEncoder.encode(registerRequestDTO.getPassword()));

        //get role from registerRequest
        Set<String> strRoles = null;
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = authService.findRole(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = authService.findRole(ERole.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = authService.findRole(ERole.ROLE_MODERATOR);
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = authService.findRole(ERole.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        authService.saveUser(user);

        return ResponseEntity.ok(new RegisterResponseDTO("User registered successfully!"));
    }
    @CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
    @GetMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {

        String currentUserEmail = jwtUtils.getUserNameFromRefreshToken(refreshToken);

        Token newAccessToken = jwtUtils.generateAccessToken(currentUserEmail);
        Token newRefreshToken = jwtUtils.generateRefreshToken(currentUserEmail);

        User user = authService.findUser(currentUserEmail);
        user.setTokenRefresh(newRefreshToken.getTokenValue());
        authService.saveUser(user);

        HttpHeaders responseHeaders = cookieUtil.createTokenCookieHeader(newRefreshToken);

        LoginResponseDTO loginResponseDTO = AuthMapper.toLoginResponseDTO(user, newAccessToken);

        return ResponseEntity.ok().headers(responseHeaders).body(loginResponseDTO);
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> logout(Principal principal) {
        User user = authService.findUser(principal.getName());
        user.setTokenRefresh(null);
        authService.saveUser(user);
        return ResponseEntity.ok().body("logout is success!");
    }

}