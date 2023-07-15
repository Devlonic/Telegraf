package com.telegraph.authentication.controllers;

import com.telegraph.authentication.models.User;
import com.telegraph.authentication.payload.request.JwtRequest;
import com.telegraph.authentication.payload.request.LoginRequest;
import com.telegraph.authentication.payload.response.JwtResponse;
import com.telegraph.authentication.rep.UserRepository;
import com.telegraph.authentication.security.jwt.JwtUtils;
import com.telegraph.authentication.security.services.UserDetailsImpl;
import com.telegraph.authentication.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody JwtRequest token) {
        if (jwtUtils.validateJwtToken(token.getAccessToken())) {
            User temp = userService.getUserByName(jwtUtils.getUserNameFromJwtToken(token.getAccessToken())).get();
            return ResponseEntity.ok(new JwtResponse(token.getAccessToken(),
                    temp.getId(),
                    temp.getName(),
                    temp.getEmail()));
        } else return ResponseEntity.ok("JWT signature does not match locally computed signature");
    }

    @PostMapping("/sign")
    public ResponseEntity<?> sign(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }


}