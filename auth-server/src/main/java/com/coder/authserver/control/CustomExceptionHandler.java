package com.coder.authserver.control;

import com.coder.authserver.payload.ApiError;
import com.coder.authserver.payload.RegisterResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = UsernameNotFoundException.class)
//    public String handle(final UsernameNotFoundException exception) {
//        System.out.println("ошибка");
//        return exception.getMessage();
//    }AuthenticationException
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> handle2(final BadCredentialsException exception) {
//        System.out.println("ошибка");
//        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "User not found", exception), HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handle(final BadCredentialsException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST, "Error: No correct email or password!", exception));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handle2(final ExpiredJwtException exception) {
        System.out.println("exception!");
        return ResponseEntity
                .badRequest()
                .body(new ApiError(HttpStatus.BAD_REQUEST, "Error: JWT token is expired!", exception));
    }
}