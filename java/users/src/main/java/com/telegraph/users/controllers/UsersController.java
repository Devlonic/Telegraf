package com.telegraph.users.controllers;

import com.telegraph.users.models.User;
import com.telegraph.users.payload.request.JwtRequest;
import com.telegraph.users.payload.request.LoginRequest;
import com.telegraph.users.payload.request.SignupRequest;
import com.telegraph.users.payload.response.JwtResponse;
import com.telegraph.users.payload.response.MessageResponse;
import com.telegraph.users.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    static final String authUrl = "http://localhost:8080/";

    @Autowired
    private RestTemplate authTemplate;

    @PostMapping("/signin")

    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<LoginRequest> entity = new HttpEntity<LoginRequest>(loginRequest, headers);
        JwtResponse jwt = authTemplate.exchange(authUrl + "api/v1/auth/sign", HttpMethod.POST, entity, JwtResponse.class).getBody();


        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

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

    @GetMapping("/info")
    public ResponseEntity<?> getUserData(@RequestBody JwtRequest authToken) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<JwtRequest> entity = new HttpEntity<JwtRequest>(authToken, headers);

        try {
            JwtResponse isValid = authTemplate.exchange(authUrl + "api/v1/auth/validate", HttpMethod.POST, entity, JwtResponse.class).getBody();
            return ResponseEntity.ok(userRepository.findByName(isValid.getName()).get());
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Bad JWT token"));
        }

    }

}