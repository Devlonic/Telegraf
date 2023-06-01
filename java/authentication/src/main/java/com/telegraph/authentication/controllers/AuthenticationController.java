package com.telegraph.authentication.controllers;

import com.telegraph.authentication.payload.request.LoginRequest;
import com.telegraph.authentication.payload.request.SignupRequest;
import com.telegraph.authentication.payload.response.JwtResponse;
import com.telegraph.authentication.payload.response.MessageResponse;
import com.telegraph.authentication.rep.UserRepository;
import com.telegraph.authentication.models.User;
import com.telegraph.authentication.security.jwt.AuthEntryPointJwt;
import com.telegraph.authentication.security.jwt.JwtUtils;
import com.telegraph.authentication.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Name is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData( @RequestBody String authToken) {

        if (!jwtUtils.validateJwtToken(authToken)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Bad JWT token"));
        }


        return ResponseEntity.ok(new MessageResponse(  userRepository.findByName(jwtUtils.getUserNameFromJwtToken(authToken)).get().toString()  ));
    }

}