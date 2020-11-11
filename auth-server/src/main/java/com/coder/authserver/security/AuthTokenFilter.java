package com.coder.authserver.security;

import com.coder.authserver.repo.UserRepository;
import com.coder.authserver.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private JwtUtils jwtUtils;

    private UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    public AuthTokenFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            if (httpServletRequest.getRequestURI().equals("/auth/refresh")) {
                String refreshToken = jwtUtils.getRefreshTokenFromCookie(httpServletRequest);
                System.out.println(refreshToken);
            //    String refreshToken = parseAccessToken(httpServletRequest);

                if (refreshToken != null && jwtUtils.validateRefreshToken(refreshToken)) {
                    String email = jwtUtils.getUserNameFromRefreshToken(refreshToken);

                    //сравниваем с бд
                    String tokenRefreshDb = userRepository.findByEmail(email)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email)).getTokenRefresh();

                    if (tokenRefreshDb != null && tokenRefreshDb.equals(refreshToken)) {


                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } else {

                String accessToken = parseAccessToken(httpServletRequest);

                if (accessToken != null && jwtUtils.validateAccessToken(accessToken)) {
                    String email = jwtUtils.getUserNameFromAccessToken(accessToken);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseAccessToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
